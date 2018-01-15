package br.pucrio.opus.refresh.services;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import br.pucrio.opus.refresh.builder.RefreshNature;

public class WorkspaceService {
	public List<IJavaProject> getRefreshProjects() throws CoreException {
		List<IJavaProject> projectList = new LinkedList<IJavaProject>();
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();
		for(IProject project : projects) {
			if(project.isOpen() && project.hasNature(JavaCore.NATURE_ID) 
					&& project.hasNature(RefreshNature.NATURE_ID)) {
				projectList.add(JavaCore.create(project));
			}
		}
		return projectList;
	}
}
