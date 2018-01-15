package br.pucrio.opus.organic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import br.pucrio.opus.organic.collector.ClassLevelSmellDetector;
import br.pucrio.opus.organic.collector.MethodLevelSmellDetector;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.AggregateMetricValues;
import br.pucrio.opus.organic.metrics.MethodMetricValueCollector;
import br.pucrio.opus.organic.metrics.TypeMetricValueCollector;
import br.pucrio.opus.organic.resources.Method;
import br.pucrio.opus.organic.resources.Resource;
import br.pucrio.opus.organic.resources.SourceFile;
import br.pucrio.opus.organic.resources.Type;

public class OrganicDetectionService {
	private static final OrganicDetectionService singleton;
	private Map<IJavaElement, Resource> resources;
	
	static {
		singleton = new OrganicDetectionService();
	}
	
	private OrganicDetectionService() {
	}
	
	public static OrganicDetectionService getInstance() {
		return singleton;
	}
	
	public Resource getResource(IJavaElement javaElement) {
		return this.resources.get(javaElement);
	}
	
	private List<Type> getAllTypes(List<ICompilationUnit> units) {
		List<Type> allTypes = new ArrayList<>();
		for (ICompilationUnit iCompUnit : units) {
			final ASTParser parser = ASTParser.newParser(AST.JLS9); 
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(iCompUnit);
			parser.setResolveBindings(true); // we need bindings later on
			CompilationUnit unit = (CompilationUnit) parser.createAST(null);
			SourceFile sourceFile = new SourceFile(unit);
			for (Type type : sourceFile.getTypes()) {
				allTypes.add(type);
				resources.put(type.getBinding().getJavaElement(), type);
				for (Method method : type.getMethods()) {
					resources.put(method.getBinding().getJavaElement(), method);
				}
			}
		}
		return allTypes;
	}
	
	private void detectSmells(List<Type> allTypes) {
		for (Type type : allTypes) {
			// It is important for some detectors that method-level smells are collected first
			for (Method method: type.getMethods()) {
				MethodLevelSmellDetector methodSmellDetector = new MethodLevelSmellDetector();
				List<Smell> smells = methodSmellDetector.detect(method);
				method.addAllSmells(smells);
				if (!smells.isEmpty()) {
					//TODO delete
					System.out.println(method.getFullyQualifiedName() + " " + smells.toString());
				}
			}
			// some class-level detectors use method-level smells in their algorithms
			ClassLevelSmellDetector classSmellDetector = new ClassLevelSmellDetector();
			List<Smell> smells = classSmellDetector.detect(type);
			type.addAllSmells(smells);
			if (!smells.isEmpty()) {
				//TODO delete
				System.out.println(type.getFullyQualifiedName() + " " + smells.toString());
			}
		}
	}
	
	private void collectMethodMetrics(Type type) {
		for (Method method: type.getMethods()) {
			MethodMetricValueCollector methodCollector = new MethodMetricValueCollector(type.getNodeAsTypeDeclaration());
			methodCollector.collect(method);
		}
	}

	private void collectTypeMetrics(List<Type> types) throws IOException {
		for (Type type : types) {
			TypeMetricValueCollector typeCollector = new TypeMetricValueCollector();
			typeCollector.collect(type);
			this.collectMethodMetrics(type);
		}
	}
	
	public void detect(List<ICompilationUnit> units) throws IOException {
		this.resources = new HashMap<>();
		AggregateMetricValues.getInstance().reset();
		List<Type> allTypes = getAllTypes(units);
		this.collectTypeMetrics(allTypes);
		this.detectSmells(allTypes);
	}
}