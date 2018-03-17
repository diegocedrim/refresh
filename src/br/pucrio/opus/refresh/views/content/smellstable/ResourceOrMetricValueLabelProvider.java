package br.pucrio.opus.refresh.views.content.smellstable;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.MetricValue;

public class ResourceOrMetricValueLabelProvider extends ColumnLabelProvider {

	@Override
	protected String getText(Object element) {
		if (element instanceof Smell) {
			return ((Smell)element).getResource().getFullyQualifiedName();
		} else {
			return ((MetricValue)element).getValue().toString();
		}
	}

}
