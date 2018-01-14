package br.pucrio.opus.organic.metrics.calculators;

import org.eclipse.jdt.core.dom.ASTNode;

import br.pucrio.opus.organic.ast.visitors.CyclomaticComplexityVisitor;
import br.pucrio.opus.organic.metrics.MetricName;

public class CyclomaticComplexityCalculator extends MetricValueCalculator {
	
	@Override
	protected Double computeValue(ASTNode target) {
		CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
		target.accept(visitor);
		return visitor.getCyclomaticComplexity().doubleValue();
	}

	@Override
	public MetricName getMetricName() {
		return MetricName.CC;
	}
	
	@Override
	public boolean shouldComputeAggregate() {
		return true;
	}

}
