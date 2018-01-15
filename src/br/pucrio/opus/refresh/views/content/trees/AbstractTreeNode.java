package br.pucrio.opus.refresh.views.content.trees;

public abstract class AbstractTreeNode implements TreeNode {
	private TreeNode parent;
	
	public AbstractTreeNode(TreeNode parent) {
		this.parent = parent;
	}

	@Override
	public Object getParent() {
		return parent;
	}
}
