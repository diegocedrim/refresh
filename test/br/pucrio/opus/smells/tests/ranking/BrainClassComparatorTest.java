package br.pucrio.opus.smells.tests.ranking;

import org.junit.Assert;
import org.junit.Test;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.collector.SmellName;
import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.smells.ranking.BrainClassComparator;

public class BrainClassComparatorTest {

	@Test(expected=NullPointerException.class)
	public void compareEqualsNullBrainClass() {
		Smell b1 = new Smell(SmellName.BrainClass);
		Smell b2 = new Smell(SmellName.BrainClass);
		new BrainClassComparator().compare(b1, b2);
	}
	
	@Test
	public void compareEqualsFilledBrainClass() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 2d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(0, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareBMCHiger() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 2d);
		b1.addMetricValue(MetricName.WMC, 2d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareWMCHiger() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 3d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareTCCHiger() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 3d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.01d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareCLOCHiger() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 2d);
		b1.addMetricValue(MetricName.CLOC, 4d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareBMCLower() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 0d);
		b1.addMetricValue(MetricName.WMC, 2d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(-1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareWMCLower() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 1d);
		b1.addMetricValue(MetricName.CLOC, 3d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(-1, new BrainClassComparator().compare(b1, b2));
	}
	
	@Test
	public void compareCLOCLower() {
		Smell b1 = new Smell(SmellName.BrainClass);
		b1.addMetricValue(MetricName.BMC, 1d);
		b1.addMetricValue(MetricName.WMC, 2d);
		b1.addMetricValue(MetricName.CLOC, 2d);
		b1.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Smell b2 = new Smell(SmellName.BrainClass);
		b2.addMetricValue(MetricName.BMC, 1d);
		b2.addMetricValue(MetricName.WMC, 2d);
		b2.addMetricValue(MetricName.CLOC, 3d);
		b2.addInverseMetricValue(MetricName.TCC, 0.5d, 0d, 1d);
		
		Assert.assertEquals(-1, new BrainClassComparator().compare(b1, b2));
	}
}
