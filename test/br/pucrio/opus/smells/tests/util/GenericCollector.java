package br.pucrio.opus.smells.tests.util;

import br.pucrio.opus.organic.metrics.MethodMetricValueCollector;
import br.pucrio.opus.organic.metrics.TypeMetricValueCollector;
import br.pucrio.opus.organic.resources.Method;
import br.pucrio.opus.organic.resources.Type;

public class GenericCollector {

	public static void collectTypeMetricValues(Type type) {
		TypeMetricValueCollector collector = new TypeMetricValueCollector();
		collector.collect(type);
	}
	
	public static void collectTypeAndMethodsMetricValues(Type type) {
		TypeMetricValueCollector collector = new TypeMetricValueCollector();
		collector.collect(type);
		for (Method method : type.getMethods()) {
			MethodMetricValueCollector mColl = new MethodMetricValueCollector(type.getNodeAsTypeDeclaration());
			mColl.collect(method);
		}
	}
}
