package br.pucrio.opus.refresh.views.content.trees;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class PackageRootTreeNode extends AbstractTreeNode {
	
	private IPackageFragmentRoot packageRoot;
	
	public PackageRootTreeNode(TreeNode parent, IPackageFragmentRoot packageRoot) {
		super(parent);
		this.packageRoot = packageRoot;
	}
	
	private IPackageFragment[] getFragments() {
		try {
			Object[] objects = this.packageRoot.getChildren();
			IPackageFragment[] fragments = new IPackageFragment[objects.length];
			for (int i = 0; i < objects.length; i++) {
				fragments[i] = (IPackageFragment)objects[i];
			}
			return fragments;
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
	}

	@Override
	public Object[] getChildren() {
		IPackageFragment[] fragments = getFragments();
		if (fragments == null) {
			return new Object[0]; 
		}
		Object[] nodes = new Object[fragments.length];
		for (int i = 0; i < fragments.length; i++) {
			nodes[i] = new PackageFragmentTreeNode(this, fragments[i]);
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		IPackageFragment[] fragments = getFragments();
		return fragments != null && fragments.length > 0;
	}

	@Override
	public String getText() {
		return packageRoot.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.sourceFolder();
	}

}
