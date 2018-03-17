package br.pucrio.opus.refresh.views.content.smellstable;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

public abstract class ColumnLabelProvider extends CellLabelProvider {
	
	protected abstract String getText(Object element);

	@Override
	public void update(ViewerCell cell) {
		cell.setText(getText(cell.getElement()));
	}

}
