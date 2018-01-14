package br.pucrio.opus.organic.metrics;

import br.pucrio.opus.organic.metrics.calculators.ClassLOCCalculator;
import br.pucrio.opus.organic.metrics.calculators.IsClassAbstract;
import br.pucrio.opus.organic.metrics.calculators.LCOM2Calculator;
import br.pucrio.opus.organic.metrics.calculators.LCOM3Calculator;
import br.pucrio.opus.organic.metrics.calculators.NOAMCalculator;
import br.pucrio.opus.organic.metrics.calculators.OverrideRatioCalculator;
import br.pucrio.opus.organic.metrics.calculators.PublicFieldCountCalculator;
import br.pucrio.opus.organic.metrics.calculators.TCCMetricValueCalculator;
import br.pucrio.opus.organic.metrics.calculators.WMCCalculator;
import br.pucrio.opus.organic.metrics.calculators.WOCCalculator;

public class TypeMetricValueCollector extends MetricValueCollector {

	public TypeMetricValueCollector() {
		addCalculator(new TCCMetricValueCalculator());
		addCalculator(new PublicFieldCountCalculator());
		addCalculator(new ClassLOCCalculator());
		addCalculator(new OverrideRatioCalculator());
		addCalculator(new IsClassAbstract());
		addCalculator(new NOAMCalculator());
		addCalculator(new WMCCalculator());
		addCalculator(new WOCCalculator());
		addCalculator(new LCOM2Calculator());
		addCalculator(new LCOM3Calculator());
	}
	
	
}
