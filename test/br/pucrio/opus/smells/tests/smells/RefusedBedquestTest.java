package br.pucrio.opus.smells.tests.smells;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.pucrio.opus.organic.collector.RefusedBequest;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.collector.SmellName;
import br.pucrio.opus.organic.resources.Type;
import br.pucrio.opus.smells.tests.util.GenericCollector;
import br.pucrio.opus.smells.tests.util.TypeLoader;

public class RefusedBedquestTest {
	
	@Test
	public void ccTest() throws Exception {
		Type type = TypeLoader.loadOne(new File("test/br/pucrio/opus/smells/tests/dummy/CC.java"));
		GenericCollector.collectTypeMetricValues(type);
		RefusedBequest smellDetector = new RefusedBequest();
		List<Smell> smells = smellDetector.detect(type);
		Assert.assertEquals(0, smells.size());
	}
	
	@Test
	public void fieldDeclarationTest() throws Exception {
		Type type = TypeLoader.loadOne(new File("test/br/pucrio/opus/smells/tests/dummy/FieldAccessedByMethod.java"));
		GenericCollector.collectTypeMetricValues(type);
		RefusedBequest smellDetector = new RefusedBequest();
		List<Smell> smells = smellDetector.detect(type);
		Assert.assertEquals(0, smells.size());
	}
	
	@Test
	public void methodLocalityTest() throws Exception {
		Type type = TypeLoader.loadOne(new File("test/br/pucrio/opus/smells/tests/dummy/RefusedBedquestSample.java"));
		GenericCollector.collectTypeMetricValues(type);
		RefusedBequest smellDetector = new RefusedBequest();
		List<Smell> smells = smellDetector.detect(type);
		Smell smell = smells.get(0);
		Assert.assertEquals(1, smells.size());
		Assert.assertEquals(SmellName.RefusedBequest, smell.getName());
	}
}
