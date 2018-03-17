package br.pucrio.opus.organic.metrics;

import br.pucrio.opus.organic.collector.Smell;

public class MetricValue {
	private MetricName metric;
	
	private Double value;
	
	private Smell smell;

	public MetricValue(MetricName metric, Double value, Smell smell) {
		super();
		this.metric = metric;
		this.value = value;
		this.smell = smell;
	}

	public MetricName getMetric() {
		return metric;
	}

	public void setMetric(MetricName metric) {
		this.metric = metric;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public Smell getSmell() {
		return smell;
	}
	
	public void setSmell(Smell smell) {
		this.smell = smell;
	}
}
