package br.pucrio.opus.refresh.views.content.trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class ProjectTreeNode extends AbstractTreeNode {
	
	private IJavaProject project;
	
	public ProjectTreeNode(IJavaProject project) {
		super(null);
		this.project = project;
	}
	
	private List<IPackageFragmentRoot> getAllSmellySourcePackageFragmentRoots() {
		try {
			List<IPackageFragmentRoot> roots = new ArrayList<>();
			for (IPackageFragmentRoot root : this.project.getAllPackageFragmentRoots()) {
				OrganicDetectionService service = OrganicDetectionService.getInstance();
				if (root.getKind() != IPackageFragmentRoot.K_SOURCE) {
					continue;
			    }
				if (service.isSmelly(root)) {
					roots.add(root);
				}
			}
			return roots;
		} catch (JavaModelException e) {
			RefreshLogger.getInstance().logError(e);
			return new ArrayList<>();
		}
	}

	@Override
	public Object[] getChildren() {
		List<IPackageFragmentRoot> roots = this.getAllSmellySourcePackageFragmentRoots();
		Object[] children = new Object[roots.size()];
		for (int i = 0; i < children.length; i++) {
			children[i] = new PackageRootTreeNode(this, roots.get(i));
		}
		return children;
	}

	@Override
	public boolean hasChildren() {
		return this.getAllSmellySourcePackageFragmentRoots().size() > 0;
	}

	@Override
	public String getText() {
		return project.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.project();
	}


}
