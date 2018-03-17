package br.pucrio.opus.refresh.tests.recommendations.extractmethod;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Before;
import org.junit.Test;

import br.pucrio.opus.organic.ast.visitors.MethodCollector;
import br.pucrio.opus.organic.ast.visitors.TypeDeclarationCollector;
import br.pucrio.opus.refresh.recommendations.extractmethod.LinesCluster;
import br.pucrio.opus.refresh.recommendations.extractmethod.LinesClusterFinder;
import br.pucrio.opus.smells.tests.util.CompilationUnitLoader;

public class LinesClusterFinderTest {
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
	public void run() {
		MethodDeclaration decl = findMethodByName("statement");
		LinesClusterFinder finder = new LinesClusterFinder();
		List<LinesCluster> clusters = finder.findClusters(decl);
		for (LinesCluster linesCluster : clusters) {
			System.out.println(linesCluster);
		}
	}
}
