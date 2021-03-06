package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.metrics.Thresholds;
import br.pucrio.opus.organic.resources.Resource;

/**
 * Shotgun Surgery resembles Divergent Change but is actually the opposite smell. 
 * Divergent Change is when many changes are made to a single class. Shotgun Surgery 
 * refers to when a single change is made to multiple classes simultaneously.
 * 
 * @author Diego Cedrim
 */
public class ShotgunSurgery extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double cc = resource.getMetricValue(MetricName.CC);
		Double cm = resource.getMetricValue(MetricName.ChangingMethods);
		if (cc == null || cm == null) {
			return new ArrayList<>();
		}
		
		if (cm > Thresholds.SHORT_MEMORY_CAP && cc > Thresholds.MANY) {
			StringBuilder builder = new StringBuilder();
			builder.append("CM > " + Thresholds.SHORT_MEMORY_CAP);
			builder.append(", CC > " + Thresholds.MANY);
			
			Smell smell = super.createSmell(resource);
			smell.addMetricValue(MetricName.ChangingMethods, cm);
			smell.addMetricValue(MetricName.CC, cc);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.ShotgunSurgery;
	}

}
