package br.pucrio.opus.organic.smells.ranking;

import java.util.Comparator;
import java.util.List;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.metrics.MetricName;

public abstract class OrderedMetricValuesComparator implements Comparator<Smell> {
	
	protected abstract List<MetricName> getMetricOrdering();

	@Override
	public int compare(Smell o1, Smell o2) {
		for (MetricName metricName : this.getMetricOrdering()) {
			Double o1Value = o1.getMetricValue(metricName);
			Double o2Value = o2.getMetricValue(metricName);
			if (!o1Value.equals(o2Value)) {
				return o1Value.compareTo(o2Value);
			}
		}
		return 0;
	}

}
