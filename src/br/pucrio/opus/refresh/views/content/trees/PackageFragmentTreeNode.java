package br.pucrio.opus.refresh.views.content.trees;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class PackageFragmentTreeNode extends AbstractTreeNode {
	private IPackageFragment packageFragment;

	public PackageFragmentTreeNode(TreeNode parent, IPackageFragment packageFragment) {
		super(parent);
		this.packageFragment = packageFragment;
	}
	
	private ICompilationUnit[] getCompilationUnits() {
		try {
			ICompilationUnit[] units = packageFragment.getCompilationUnits();
			return units;
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
	}

	@Override
	public String getText() {
		return packageFragment.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.packageFragment();
	}

	@Override
	public Object[] getChildren() {
		ICompilationUnit[] units = getCompilationUnits();
		if (units == null) {
			return new Object[0];
		}
		CompilationUnitTreeNode[] nodes = new CompilationUnitTreeNode[units.length];
		for (int i = 0; i < units.length; i++) {
			nodes[i] = new CompilationUnitTreeNode(this, units[i]);
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		ICompilationUnit[] units = getCompilationUnits();
		return units != null && units.length > 0;
	}

}
