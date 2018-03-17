package br.pucrio.opus.refresh.views;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import br.pucrio.opus.refresh.builder.SmellDetectorJob;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.content.elementstree.AbstractTreeNode;
import br.pucrio.opus.refresh.views.content.elementstree.SmellsTreeContentProvider;


public class SmellsTreeView extends ViewPart implements IJobChangeListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "br.pucrio.opus.refresh.views.SmellsTreeView";

	@Inject IWorkbench workbench;

	private TreeViewer viewer;
	private Action doubleClickAction;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new SmellsTreeContentProvider(this));
		viewer.setInput(getViewSite());
		viewer.setLabelProvider(new RefreshLabelProvider());

		getSite().setSelectionProvider(viewer);
		makeActions();
		hookDoubleClickAction();
		Job.getJobManager().addJobChangeListener(this);
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				
				if (obj instanceof AbstractTreeNode) {
					AbstractTreeNode node = (AbstractTreeNode)obj;
					try {
						JavaUI.openInEditor(node.getJavaElement());
					} catch (PartInitException e) {
						RefreshLogger.getInstance().logError(e);
					} catch (JavaModelException e) {
						RefreshLogger.getInstance().logError(e);
					}
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {

	}

	@Override
	public void awake(IJobChangeEvent event) {

	}

	@Override
	public void done(IJobChangeEvent event) {
		if (event.getJob().belongsTo(SmellDetectorJob.SMELLS_FAMILY)) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					viewer.refresh();
				}
			});
		}
	}

	@Override
	public void running(IJobChangeEvent event) {

	}

	@Override
	public void scheduled(IJobChangeEvent event) {

	}

	@Override
	public void sleeping(IJobChangeEvent event) {

	}
}
