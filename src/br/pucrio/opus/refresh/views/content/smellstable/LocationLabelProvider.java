package br.pucrio.opus.refresh.views.content.smellstable;

import br.pucrio.opus.organic.collector.Smell;

public class LocationLabelProvider extends ColumnLabelProvider {

	@Override
	protected String getText(Object element) {
		if (element instanceof Smell) {
			return ((Smell)element).getStartingLine().toString();
		} 
		return null;
	}

}
