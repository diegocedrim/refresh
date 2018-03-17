package br.pucrio.opus.refresh.tests.recommendations.extractmethod;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.pucrio.opus.organic.ast.visitors.MethodCollector;
import br.pucrio.opus.organic.ast.visitors.TypeDeclarationCollector;
import br.pucrio.opus.refresh.recommendations.extractmethod.LinesCluster;
import br.pucrio.opus.smells.tests.util.CompilationUnitLoader;

public class LinesClusterTest {

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
	
	private boolean isViable(int start, int end) {
		MethodDeclaration decl = findMethodByName("statement");
		LinesCluster cluster = new LinesCluster(start, end);
		return cluster.isViable((CompilationUnit)decl.getRoot());
	}

	@Test
	public void statement12To15Test() {
		Assert.assertTrue(isViable(12, 15));
	}
	
	@Test
	public void statement12To17Test() {
		Assert.assertFalse(isViable(12, 17));
	}
	
	@Test
	public void statement20To21Test() {
		Assert.assertFalse(isViable(20, 21)); //TODO precisa corrigir depois
	}
	
	@Test
	public void statement18To24est() {
		Assert.assertTrue(isViable(18, 24));
	}
	
	@Test
	public void statement22To23Test() {
		Assert.assertFalse(isViable(22, 23));
	}
	
	@Test
	public void statement15To23Test() {
		Assert.assertFalse(isViable(15, 23));
	}

}

