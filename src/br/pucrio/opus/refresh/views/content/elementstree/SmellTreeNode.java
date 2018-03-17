package br.pucrio.opus.refresh.views.content.elementstree;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.collector.Smell;

public class SmellTreeNode extends AbstractTreeNode {
	
	private Smell smell;
	
	private IJavaElement affected;

	public SmellTreeNode(TreeNode parent, Smell smell, IJavaElement affected) {
		super(parent);
		this.smell = smell;
		this.affected = affected;
	}

	@Override
	public Object[] getChildren() {
		return new Object[0];
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public String getText() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(smell.getName().toString());
		buffer.append(" : ");
		buffer.append(smell.getReason());
		return buffer.toString();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IJavaElement getJavaElement() {
		return affected;
	}
}
