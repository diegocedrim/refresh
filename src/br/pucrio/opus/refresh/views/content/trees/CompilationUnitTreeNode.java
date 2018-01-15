package br.pucrio.opus.refresh.views.content.trees;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class CompilationUnitTreeNode extends AbstractTreeNode {
	private ICompilationUnit compilationUnit;
	
	public CompilationUnitTreeNode(TreeNode parent, ICompilationUnit compilationUnit) {
		super(parent);
		this.compilationUnit = compilationUnit;
	}
	
	private IType[] getTypes() {
		try {
			return compilationUnit.getAllTypes();
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
	}

	@Override
	public Object[] getChildren() {
		IType[] types = getTypes();
		if (types == null) {
			return new Object[0];
		}
		Object[] nodes = new Object[types.length];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new TypeTreeNode(this, types[i]);
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		IType[] types = getTypes();
		return types != null && types.length > 0;
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
