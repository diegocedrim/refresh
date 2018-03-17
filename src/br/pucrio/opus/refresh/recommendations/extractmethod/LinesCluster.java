package br.pucrio.opus.refresh.recommendations.extractmethod;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class LinesCluster {
	/**
	 * Line where the cluster starts
	 */
	private int start;
	
	/**
	 * Line where the cluster ends
	 */
	private int end;

	public LinesCluster(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public boolean overlaps(LinesCluster cluster) {
		return (start >= cluster.start && start <= cluster.end) 
				|| (cluster.start >= start && cluster.start <= end);
	}
	
	/**
	 * Creates a new cluster by merging two others
	 * @param one first one
	 * @param other second one
	 * @return new cluster
	 */
	public static LinesCluster merge(LinesCluster one, LinesCluster other) {
		return new LinesCluster(Math.min(one.start, one.end), Math.max(one.end, other.end));
	}
	
	/**
	 * Returns the delimiting parent of the statement, i.e, any possible
	 * construct that must have all the lines of the cluster. If the cluster
	 * have lines belonging to different delimiting parents, than the cluster
	 * is not viable.
	 */
	private ASTNode getDelimitingParent(CompilationUnit unit, int lineNumber) {
		int offset = unit.getPosition(lineNumber, 0);
		NodeFinder finder= new NodeFinder(unit, offset, unit.getLength());
		ASTNode node= finder.getCoveredNode();
		while (node != null 
				&& !(node instanceof Block)
				&& !(node instanceof CatchClause)
				&& !(node instanceof DoStatement)
				&& !(node instanceof EnhancedForStatement)
				&& !(node instanceof ForStatement)
				&& !(node instanceof IfStatement)
				&& !(node instanceof SwitchStatement)
				&& !(node instanceof SynchronizedStatement)
				&& !(node instanceof TryStatement)
				&& !(node instanceof WhileStatement)) {
		    node = node.getParent();
		}
		return node;
	}
	
	private boolean isDescendant(ASTNode child, ASTNode parent) {
		ASTNode node = child;
		while (node != null) {
			if (node.equals(parent)) {
				return true;
			}
			node = node.getParent();
		}
		return false;
	}
	
	/**
	 * Verifies if the cluster contains all lines of a particular node.
	 */
	private boolean containsAll(ASTNode node, CompilationUnit unit) {
		int nodeStart = unit.getLineNumber(node.getStartPosition());
		int nodeEnd = unit.getLineNumber(node.getStartPosition() + node.getLength());
		return nodeStart >= start && nodeStart <= end && nodeEnd >= start && nodeEnd <= end;
	}
	/**
	 * The cluster is viable if all lines belongs to the same block
	 * @param unit
	 * @return
	 */
	public boolean isViable(CompilationUnit unit) {
		ASTNode firstParentFound = null;
		for (int i = start; i <= end; i ++) {
			ASTNode lineParent = getDelimitingParent(unit, i);
			if (firstParentFound == null) {
				firstParentFound = lineParent;
			}
			if (!firstParentFound.equals(lineParent)
					&& (!isDescendant(lineParent, firstParentFound) || !containsAll(lineParent, unit))) {
				return false;
			}
		}
		return true;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinesCluster other = (LinesCluster) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinesCluster [start=" + start + ", end=" + end + "]";
	}
	
}
