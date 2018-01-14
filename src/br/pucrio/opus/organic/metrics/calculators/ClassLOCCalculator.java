package br.pucrio.opus.organic.metrics.calculators;

import org.eclipse.jdt.core.dom.ASTNode;

import br.pucrio.opus.organic.ast.visitors.LinesOfCodeVisitor;
import br.pucrio.opus.organic.metrics.MetricName;

public class ClassLOCCalculator extends MetricValueCalculator {
	
	@Override
	protected Double computeValue(ASTNode target) {
		LinesOfCodeVisitor visitor = new LinesOfCodeVisitor();
		target.accept(visitor);
		return visitor.getLoc().doubleValue();
	}

	@Override
	public MetricName getMetricName() {
		return MetricName.CLOC;
	}
	
	@Override
	public boolean shouldComputeAggregate() {
		return true;
	}

}
