package br.pucrio.opus.organic.collector;

import java.util.ArrayList;
import java.util.List;

import br.pucrio.opus.organic.resources.Resource;

public abstract class CompositeSmellDetector extends SmellDetector {

	private List<SmellDetector> detectors;
	
	public CompositeSmellDetector() {
		this.detectors = new ArrayList<>();
	}
	
	public void addDetector(SmellDetector detector) {
		this.detectors.add(detector);
	}
	
	@Override
	public List<Smell> detect(Resource resource) {
		List<Smell> smells = new ArrayList<>();
		for (SmellDetector detector : this.detectors) {
			smells.addAll(detector.detect(resource));
		}
		return smells;
	}
}
