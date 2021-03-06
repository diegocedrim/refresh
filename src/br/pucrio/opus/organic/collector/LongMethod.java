package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.AggregateMetricValues;
import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

/**
 * All methods having LOCs higher than the average of the system.
 * @author Diego Cedrim
 */
public class LongMethod extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
		Double methodLoc = resource.getMetricValue(MetricName.MLOC);
		Double avgMLOC = aggregate.getAverageValue(MetricName.MLOC);
		if (methodLoc > avgMLOC && methodLoc > 40) {
			StringBuilder builder = new StringBuilder();
			builder.append("MLOC > " + avgMLOC);
			
			Smell smell = super.createSmell(resource);
			smell.addMetricValue(MetricName.MLOC, methodLoc);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.LongMethod;
	}

}
