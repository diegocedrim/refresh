package br.pucrio.opus.smells.tests.ranking;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.collector.SmellName;
import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.smells.ranking.SmellComparatorReverse;

public class SmellComparatorTest {

	@Test
	public void priorityOrderingTest() {
		Smell longParameter = new Smell(SmellName.LongParameterList);
		Smell lazyClass = new Smell(SmellName.LazyClass);
		
		Smell bcSevere = new Smell(SmellName.BrainClass);
		bcSevere.addMetricValue(MetricName.BMC, 1d);
		bcSevere.addMetricValue(MetricName.WMC, 2d);
		bcSevere.addMetricValue(MetricName.CLOC, 3d);
		bcSevere.addInverseMetricValue(MetricName.TCC, 0.1d, 0d, 1d);
		
		Smell bcLight = new Smell(SmellName.BrainClass);
		bcLight.addMetricValue(MetricName.BMC, 1d);
		bcLight.addMetricValue(MetricName.WMC, 2d);
		bcLight.addMetricValue(MetricName.CLOC, 3d);
		bcLight.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		
		List<Smell> smells = Arrays.asList(longParameter, bcSevere, bcLight, lazyClass);
		Collections.sort(smells, new SmellComparatorReverse());
		Assert.assertSame(bcSevere, smells.get(0));
		Assert.assertSame(bcLight, smells.get(1));
		Assert.assertSame(lazyClass, smells.get(2));
		Assert.assertSame(longParameter, smells.get(3));
	}
}
