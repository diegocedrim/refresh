package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

/**
 * All classes overriding more than half of the methods inherited by a superclass
 * @author Diego Cedrim
 */
public class RefusedBequest extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double overrideRatio = resource.getMetricValue(MetricName.OverrideRatio);
		if (overrideRatio != null && overrideRatio > 0.5) {
			StringBuilder builder = new StringBuilder();
			builder.append("OVERRIDE_RATIO = " + overrideRatio);
			
			Smell smell = super.createSmell(resource);
			smell.addMetricValue(MetricName.OverrideRatio, overrideRatio);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.RefusedBequest;
	}

}
