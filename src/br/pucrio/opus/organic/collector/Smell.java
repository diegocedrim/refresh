package br.pucrio.opus.organic.collector;

import java.util.HashMap;
import java.util.Map;

import br.pucrio.opus.organic.metrics.MetricName;
import br.pucrio.opus.organic.resources.Resource;

public class Smell {

	private SmellName name;
	
	private String reason;
	
	/**
	 * Metrics used to detect the smell and their respective values
	 */
	private Map<MetricName, Double> metricValues;
	
	/**
	 * Line of code where the smell starts to appear
	 */
	private Integer startingLine;
	
	private Integer endingLine;
	
	private Resource resource;
	
	public Smell(SmellName name) {
		this.metricValues = new HashMap<>();
		this.name = name;
	}
	
	public Smell(SmellName name, String reason) {
		this.metricValues = new HashMap<>();
		this.name = name;
		this.reason = reason;
	}
	
	public Smell(SmellName name, String reason, Integer line) {
		this.metricValues = new HashMap<>();
		this.name = name;
		this.reason = reason;
		this.startingLine = line;
	}
	
	public void addMetricValue(MetricName name, Double value) {
		this.metricValues.put(name, value);
	}
	
	/**
	 * Adds the inverse value of the metric (1/value). This happens when
	 * a metric is good when the value is higher. We want to have in the map
	 * only values that increases when the smell is worse.
	 * @param name name of the metric
	 * @param value value of the metric
	 */
	public void addInverseMetricValue(MetricName name, Double value, Double min, Double max) {
		if (value <= min) {
			this.metricValues.put(name, max);
		} else if (value >= max) {
			this.metricValues.put(name, min);
		} else {
			this.metricValues.put(name, max/value);
		}
		
	}
	
	public Double getMetricValue(MetricName name) {
		return this.metricValues.get(name);
	}

	public SmellName getName() {
		return name;
	}

	public void setName(SmellName name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getStartingLine() {
		return startingLine;
	}

	public void setStartingLine(Integer line) {
		this.startingLine = line;
	}

	public Integer getEndingLine() {
		return endingLine;
	}

	public void setEndingLine(Integer endingLine) {
		this.endingLine = endingLine;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "Smell [name=" + name + ", metricValues=" + metricValues + ", resource=" + resource + "]";
	}
}
