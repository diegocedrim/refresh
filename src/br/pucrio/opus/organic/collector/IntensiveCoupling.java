package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.metrics.Thresholds;
import br.pucrio.opus.organic.resources.Resource;

/**
 * Intensive Coupling occurs when a method is tied to many other operations in the system, 
 * whereby these provider operations are dispersed only into one or a few classes.
 * 
 * @author Diego Cedrim
 */
public class IntensiveCoupling extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double cint = resource.getMetricValue(MetricName.CINT);
		Double cdisp = resource.getMetricValue(MetricName.CDISP);
		Double maxNesting = resource.getMetricValue(MetricName.MaxNesting);
		
		boolean callsManyDispersedInFew = cint > Thresholds.SHORT_MEMORY_CAP && cdisp < Thresholds.HALF;
		boolean callsMoreThanFewInVeryFewClasses = cint > Thresholds.FEW && cdisp < Thresholds.ONE_QUARTER;
		if (callsManyDispersedInFew || callsMoreThanFewInVeryFewClasses) {
			if (maxNesting > Thresholds.SHALLOW) {
				StringBuilder builder = new StringBuilder();
				builder.append("CINT = " + cint);
				builder.append(", CDISP = " + cdisp);
				builder.append(", CC > " + Thresholds.SHALLOW);
				
				Smell smell = super.createSmell(resource);
				smell.addMetricValue(MetricName.CINT, cint);
				smell.addMetricValue(MetricName.CDISP, cdisp);
				smell.addMetricValue(MetricName.CC, maxNesting);
				
				smell.setReason(builder.toString());
				return Arrays.asList(smell);
			}
		}
		
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.IntensiveCoupling;
	}

}
