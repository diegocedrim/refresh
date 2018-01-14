package br.pucrio.opus.refresh.builder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import br.pucrio.opus.organic.collector.ClassLevelSmellDetector;
import br.pucrio.opus.organic.collector.MethodLevelSmellDetector;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.MethodMetricValueCollector;
import br.pucrio.opus.organic.metrics.TypeMetricValueCollector;
import br.pucrio.opus.organic.resources.Method;
import br.pucrio.opus.organic.resources.SourceFile;
import br.pucrio.opus.organic.resources.Type;

public class SmellDetectorJob extends Job {
	
	private IJavaProject project;

	public SmellDetectorJob(String name, IJavaProject project) {
		super(name);
		this.project = project;
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

	private void detectSmells(List<Type> allTypes) {
		for (Type type : allTypes) {
			// It is important for some detectors that method-level smells are collected first
			for (Method method: type.getMethods()) {
				MethodLevelSmellDetector methodSmellDetector = new MethodLevelSmellDetector();
				List<Smell> smells = methodSmellDetector.detect(method);
				method.addAllSmells(smells);
			}
			// some class-level detectors use method-level smells in their algorithms
			ClassLevelSmellDetector classSmellDetector = new ClassLevelSmellDetector();
			List<Smell> smells = classSmellDetector.detect(type);
			type.addAllSmells(smells);
		}
	}
	
	private List<ICompilationUnit> getProjectCompilationUnits() throws JavaModelException {
		List<ICompilationUnit> units = new ArrayList<ICompilationUnit>();
		IPackageFragment[] packages = project.getPackageFragments();
		for (IPackageFragment fragment : packages) {
			if (fragment.getKind() != IPackageFragmentRoot.K_SOURCE) {
				continue;
		    }
			for (ICompilationUnit compilationUnit : fragment.getCompilationUnits()) {
				units.add(compilationUnit);
			}
		}
		return units;
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
			}
		}
		return allTypes;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			List<ICompilationUnit> units = getProjectCompilationUnits();
			List<Type> allTypes = getAllTypes(units);
			this.collectTypeMetrics(allTypes);
			this.detectSmells(allTypes);
		} catch (JavaModelException e) {
			return Status.CANCEL_STATUS;
		} catch (IOException e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

}
