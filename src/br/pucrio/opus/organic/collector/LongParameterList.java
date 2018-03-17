package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.AggregateMetricValues;
import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

/**
 * All methods having a number of parameters higher than the average of the system.
 * @author Diego Cedrim
 */
public class LongParameterList extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		AggregateMetricValues aggregate = AggregateMetricValues.getInstance();
		Double methodParameterCount = resource.getMetricValue(MetricName.ParameterCount);
		Double avgParameterCount = aggregate.getAverageValue(MetricName.ParameterCount);
		if (methodParameterCount > avgParameterCount && methodParameterCount > 5) {
			StringBuilder builder = new StringBuilder();
			builder.append("PARAMETER_COUNT > " + avgParameterCount);
			
			Smell smell = super.createSmell(resource);
			smell.addMetricValue(MetricName.ParameterCount, methodParameterCount);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.LongParameterList;
	}

}
