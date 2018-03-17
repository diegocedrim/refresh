package br.pucrio.opus.refresh.tests.recommendations.extractmethod;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Before;
import org.junit.Test;

import br.pucrio.opus.organic.ast.visitors.MethodCollector;
import br.pucrio.opus.organic.ast.visitors.TypeDeclarationCollector;
import br.pucrio.opus.refresh.recommendations.extractmethod.VariableAndMethodCallsVisitor;
import br.pucrio.opus.smells.tests.util.CompilationUnitLoader;

public class VariableAndMethodCallsVisitorTest {

	private CompilationUnit compilationUnit;

	private List<MethodDeclaration> methods;

	private MethodDeclaration findMethodByName(String name) {
		for (MethodDeclaration decls : methods) {
			if (decls.getName().toString().equals(name)) {
				return decls;
			}
		}
		return null;
	}
	
	@Before
	public void setUp() throws IOException{
		File file = new File("test/br/pucrio/opus/smells/tests/dummy/Customer.java");
		this.compilationUnit = CompilationUnitLoader.getCompilationUnit(file);

		TypeDeclarationCollector typeVisitor = new TypeDeclarationCollector();
		this.compilationUnit.accept(typeVisitor);

		MethodCollector collector = new MethodCollector();
		compilationUnit.accept(collector);
		methods = collector.getNodesCollected();
	}

	@Test
	public void statementTest() {
		MethodDeclaration decl = findMethodByName("statement");
		VariableAndMethodCallsVisitor visitor = new VariableAndMethodCallsVisitor();
		decl.accept(visitor);
		
		List<IBinding> nodes = visitor.getNodeBindings();
		for (IBinding iBinding : nodes) {
			System.out.println(iBinding.getName() + " => " + visitor.getLinesUsing(iBinding));
			
		}
		
		//TODO IMPLEMENTAR TESTES REAIS AQUI
	}

}

