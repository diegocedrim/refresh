package br.pucrio.opus.refresh.recommendations.extractmethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import br.pucrio.opus.organic.ast.visitors.LinesOfCodeVisitor;

/**
 * Implements the algorithm in the Phase 1  
 * proposed by http://ieeexplore.ieee.org/document/7801138/
 * @author Diego Cedrim
 */
public class LinesClusterFinder {
	
	private List<LinesCluster> createClusters(List<Integer> lines, int step) {
		List<LinesCluster> clusters = new ArrayList<>();
		for (int i = 0; i < lines.size() - 1; i++) {
			int current = lines.get(i);
			int next = lines.get(i + 1);
			if (next - current <= step) {
				clusters.add(new LinesCluster(current, next));
			}
		}
		return clusters;
	}
	
	private List<LinesCluster> mergeOverlappingClusters(List<LinesCluster> clusters) {
		List<LinesCluster> merging = new ArrayList<>();
		for (int i = 0; i < clusters.size(); i++) {
			for (int j = i + 1; j < clusters.size(); j++) {
				LinesCluster one = clusters.get(i);
				LinesCluster other = clusters.get(j);
				if (one.overlaps(other)) {
					merging.add(LinesCluster.merge(one, other));
				}
			}
		}
		return merging;
	}
	
	private int getSize(MethodDeclaration declaration) {
		LinesOfCodeVisitor visitor = new LinesOfCodeVisitor();
		declaration.accept(visitor);
		return visitor.getLoc();
	}
	
	private List<LinesCluster> filterViable(Set<LinesCluster> candidates, CompilationUnit unit) {
		List<LinesCluster> finalCandidates = new ArrayList<>();
		for (LinesCluster linesCluster : candidates) {
			if (linesCluster.isViable(unit)) {
				finalCandidates.add(linesCluster);
			}
		}
		return finalCandidates;
	}
	
	public List<LinesCluster> findClusters(MethodDeclaration methodDeclaration) {
		VariableAndMethodCallsVisitor visitor = new VariableAndMethodCallsVisitor();
		methodDeclaration.accept(visitor);
		
		Set<LinesCluster> candidates = new HashSet<>();
		int size = getSize(methodDeclaration);
		for (int step = 1; step < size; step++) {
			List<IBinding> nodes = visitor.getNodeBindings();
			for (IBinding binding : nodes) {
				List<LinesCluster> clusters = createClusters(visitor.getLinesUsing(binding), step);
				List<LinesCluster> merged = mergeOverlappingClusters(clusters);
				candidates.addAll(clusters);
				candidates.addAll(merged);
			}
		}
		
		CompilationUnit unit = (CompilationUnit)methodDeclaration.getRoot();
		return filterViable(candidates, unit);
	}
}
