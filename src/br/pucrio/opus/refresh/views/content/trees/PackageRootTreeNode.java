package br.pucrio.opus.refresh.views.content.trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class PackageRootTreeNode extends AbstractTreeNode {
	
	private IPackageFragmentRoot packageRoot;
	
	public PackageRootTreeNode(TreeNode parent, IPackageFragmentRoot packageRoot) {
		super(parent);
		this.packageRoot = packageRoot;
	}
	
	private List<IPackageFragment> getSmellyFragments() {
		try {
			OrganicDetectionService service = OrganicDetectionService.getInstance();
			Object[] objects = this.packageRoot.getChildren();
			List<IPackageFragment> fragments = new ArrayList<>();
			for (int i = 0; i < objects.length; i++) {
				IPackageFragment fragment = (IPackageFragment)objects[i];
				if (service.isSmelly(fragment)) {
					fragments.add(fragment);
				}
			}
			return fragments;
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
	}

	@Override
	public Object[] getChildren() {
		List<IPackageFragment> fragments = getSmellyFragments();
		if (fragments == null || fragments.isEmpty()) {
			return new Object[0]; 
		}
		Object[] nodes = new Object[fragments.size()];
		for (int i = 0; i < fragments.size(); i++) {
			nodes[i] = new PackageFragmentTreeNode(this, fragments.get(i));
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		List<IPackageFragment> fragments = getSmellyFragments();
		return fragments != null && !fragments.isEmpty();
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
