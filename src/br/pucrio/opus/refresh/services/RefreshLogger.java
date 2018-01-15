package br.pucrio.opus.refresh.services;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import br.pucrio.opus.refresh.Activator;

public class RefreshLogger {
	private ILog logger;
	
	public RefreshLogger() {
		this.logger = Activator.getDefault().getLog();
	}
	
	public void logError(String message, Exception exception) {
		this.logger.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, exception));
	}
	
	public void logError(Exception exception) {
		this.logger.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, exception.getMessage(), exception));
	}
}
