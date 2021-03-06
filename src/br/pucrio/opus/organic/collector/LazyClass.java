package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.AggregateMetricValues;
import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

/**
 * All classes having LOCs lower than the first quartile of the distribution of LOCs for all system’s classes.
 * @author Diego Cedrim
 */
public class LazyClass extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
		Double classLOC = resource.getMetricValue(MetricName.CLOC);
		Double clocFirstQuartile = aggregate.getFirstQuartileValue(MetricName.CLOC);
		if (classLOC < clocFirstQuartile) {
			StringBuilder builder = new StringBuilder();
			builder.append("CLOC < " + clocFirstQuartile);
			
			Smell smell = super.createSmell(resource);
			smell.addInverseMetricValue(MetricName.CLOC, classLOC, 0d, aggregate.getMaxValue(MetricName.CLOC));
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.LazyClass;
	}

}
