package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

/**
 * Class data should be private: A class having at least one public field.
 * @author Diego Cedrim
 */
public class ClassDataShouldBePrivate extends SmellDetector {
	
	@Override
	public List<Smell> detect(Resource resource) {
		Double publicFieldCount = resource.getMetricValue(MetricName.PublicFieldCount);
		if (publicFieldCount != null && publicFieldCount >= 1) {
			StringBuilder builder = new StringBuilder();
			builder.append("PUBLIC_FIELD_COUNT = " + publicFieldCount);
			
			Smell smell = super.createSmell(resource);
			smell.addMetricValue(MetricName.PublicFieldCount, publicFieldCount);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.ClassDataShouldBePrivate;
	}

}
