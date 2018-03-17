package br.pucrio.opus.refresh.views;


import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.MetricValue;
import br.pucrio.opus.refresh.builder.SmellDetectorJob;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.content.smellstable.LocationLabelProvider;
import br.pucrio.opus.refresh.views.content.smellstable.ResourceOrMetricValueLabelProvider;
import br.pucrio.opus.refresh.views.content.smellstable.SmellContentProvider;
import br.pucrio.opus.refresh.views.content.smellstable.SmellOrMetricNameLabelProvider;


public class SmellsTableView extends ViewPart implements IJobChangeListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "br.pucrio.opus.refresh.views.SmellsTableView";

	@Inject IWorkbench workbench;
	
	private TreeViewer viewer;
	private Action doubleClickAction;
	 

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		
		viewer.setContentProvider(new SmellContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);
		
		TreeViewerColumn smellName = new TreeViewerColumn(viewer, SWT.NONE);
		smellName.getColumn().setWidth(200);
		smellName.getColumn().setText("Code Smell");
		smellName.setLabelProvider(new SmellOrMetricNameLabelProvider());
		
		TreeViewerColumn resource = new TreeViewerColumn(viewer, SWT.NONE);
		resource.getColumn().setWidth(200);
		resource.getColumn().setText("Resource");
		resource.setLabelProvider(new ResourceOrMetricValueLabelProvider());
		
		TreeViewerColumn location = new TreeViewerColumn(viewer, SWT.NONE);
		location.getColumn().setWidth(200);
		location.getColumn().setText("Location");
		location.setLabelProvider(new LocationLabelProvider());
		
		// Create the help context id for the viewer's control
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookDoubleClickAction();
		Job.getJobManager().addJobChangeListener(this);
		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}


	
	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
		
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				IJavaElement element;
				if (obj instanceof Smell) {
					Smell node = (Smell)obj;
					element = node.getResource().getGenericBinding().getJavaElement();
				} else {
					MetricValue value = (MetricValue)obj;
					element = value.getSmell().getResource().getGenericBinding().getJavaElement();
				}
				
				try {
					JavaUI.openInEditor(element);
				} catch (PartInitException e) {
					RefreshLogger.getInstance().logError(e);
				} catch (JavaModelException e) {
					RefreshLogger.getInstance().logError(e);
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
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Smells Severity View",
			message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	@Override
	public void done(IJobChangeEvent event) {
		if (event.getJob().belongsTo(SmellDetectorJob.SMELLS_FAMILY)) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					viewer.setInput(OrganicDetectionService.getInstance().getDetectedSmells());
					viewer.refresh();
				}
			});
		}
	}

	@Override
	public void aboutToRun(IJobChangeEvent arg0) {
		
	}

	@Override
	public void awake(IJobChangeEvent arg0) {
		
	}

	@Override
	public void running(IJobChangeEvent arg0) {
		
	}

	@Override
	public void scheduled(IJobChangeEvent arg0) {
		
	}

	@Override
	public void sleeping(IJobChangeEvent arg0) {
		
	}
}
