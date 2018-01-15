package br.pucrio.opus.refresh.views.content.trees;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class CompilationUnitTreeNode extends AbstractTreeNode {
	private ICompilationUnit compilationUnit;
	
	public CompilationUnitTreeNode(TreeNode parent, ICompilationUnit compilationUnit) {
		super(parent);
		this.compilationUnit = compilationUnit;
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
		return this.compilationUnit.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.compilationUnit();
	}

	
}
