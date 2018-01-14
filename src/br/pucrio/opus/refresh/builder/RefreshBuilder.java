package br.pucrio.opus.refresh.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class RefreshBuilder extends IncrementalProjectBuilder {
	
	public static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";

	public static final String BUILDER_ID = "br.pucrio.opus.refresh.refreshBuilder";

	private static final String MARKER_TYPE = "br.pucrio.opus.refresh.xmlProblem";

	private boolean isJavaProject() throws CoreException {
		return getProject().isNatureEnabled(JAVA_NATURE);
	}
	
	private IJavaProject getJavaProject() {
		return JavaCore.create(getProject());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		if (!isJavaProject()) {
			return null;
		}
		
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		// delete markers set and files created
		getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
	}


	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		SmellDetectorJob job = new SmellDetectorJob("detector", getJavaProject());
		job.schedule();
	}

	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		//TODO we must identify what was changed, how that could impact the smells, compute the new delta, and run the build only for it
		fullBuild(monitor);
	}
}
