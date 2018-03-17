package br.pucrio.opus.refresh.views.content.elementstree;

import org.eclipse.jdt.core.IJavaElement;

public abstract class AbstractTreeNode implements TreeNode {
	private TreeNode parent;
	
	public AbstractTreeNode(TreeNode parent) {
		this.parent = parent;
	}

	@Override
	public Object getParent() {
		return parent;
	}
	
	@Override
	public IJavaElement getJavaElement() {
		return null;
	}
	
}
