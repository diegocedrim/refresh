package br.pucrio.opus.refresh.builder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

public class SmellDetectorJob extends Job {

	public SmellDetectorJob(String name) {
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor arg0) {
		return null;
	}

}
