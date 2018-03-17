package br.pucrio.opus.refresh.views.content.elementstree;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.part.ViewPart;

import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.services.WorkspaceService;

public class SmellsTreeContentProvider implements ITreeContentProvider {
	
	private ViewPart viewPart;
	
	public SmellsTreeContentProvider(ViewPart viewPart) {
		this.viewPart = viewPart;
	}
	
	private Object[] getProjectNodes() {
		try {
			WorkspaceService service = new WorkspaceService();
			List<IJavaProject> projects = service.getRefreshProjects();
			Object[] nodes = new Object[projects.size()];
			for (int i = 0; i < nodes.length; i++) {
				nodes[i] = new ProjectTreeNode(projects.get(i));
			}
			return nodes;
		} catch (CoreException e) {
			RefreshLogger.getInstance().logError(e);
			return new Object[0];
		}
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent.equals(this.viewPart.getViewSite())) {
			return this.getProjectNodes();
		}
		TreeNode node = (TreeNode)parent;
		return node.getChildren();
	}

	@Override
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	@Override
	public Object getParent(Object child) {
		TreeNode node = (TreeNode)child;
		return node.getParent();
	}

	@Override
	public boolean hasChildren(Object parent) {
		TreeNode node = (TreeNode)parent;
		return node.hasChildren();
	}

}
