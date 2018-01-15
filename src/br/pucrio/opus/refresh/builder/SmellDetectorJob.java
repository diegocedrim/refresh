package br.pucrio.opus.refresh.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.refresh.services.RefreshLogger;

public class SmellDetectorJob extends Job {
	public static final String SMELLS_FAMILY = "smellDetectionFamily";
	
	private IJavaProject project;

	public SmellDetectorJob(String name, IJavaProject project) {
		super(name);
		this.project = project;
	}
	
	public boolean belongsTo(Object family) {
        return family.equals(SMELLS_FAMILY);
     }

	private List<ICompilationUnit> getProjectCompilationUnits() throws JavaModelException {
		List<ICompilationUnit> units = new ArrayList<ICompilationUnit>();
		IPackageFragment[] packages = project.getPackageFragments();
		for (IPackageFragment fragment : packages) {
			if (fragment.getKind() != IPackageFragmentRoot.K_SOURCE) {
				continue;
			}
			for (ICompilationUnit compilationUnit : fragment.getCompilationUnits()) {
				units.add(compilationUnit);
			}
		}
		return units;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			List<ICompilationUnit> units = getProjectCompilationUnits();
			OrganicDetectionService.getInstance().detect(units);
		} catch (JavaModelException e) {
			RefreshLogger.getInstance().logError(e);
			return Status.CANCEL_STATUS;
		} catch (IOException e) {
			RefreshLogger.getInstance().logError(e);
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

}
