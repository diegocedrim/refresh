package br.pucrio.opus.organic.collector;

import java.util.Comparator;

import br.pucrio.opus.organic.smells.ranking.BrainClassComparator;
import br.pucrio.opus.organic.smells.ranking.BrainMethodComparator;
import br.pucrio.opus.organic.smells.ranking.ClassDataShouldBePrivateComparator;
import br.pucrio.opus.organic.smells.ranking.ComplexClassComparator;
import br.pucrio.opus.organic.smells.ranking.DataClassComparator;
import br.pucrio.opus.organic.smells.ranking.DispersedCouplingComparator;
import br.pucrio.opus.organic.smells.ranking.FeatureEnvyComparator;
import br.pucrio.opus.organic.smells.ranking.GodClassComparator;
import br.pucrio.opus.organic.smells.ranking.IntensiveCouplingComparator;
import br.pucrio.opus.organic.smells.ranking.LazyClassComparator;
import br.pucrio.opus.organic.smells.ranking.LongMethodComparator;
import br.pucrio.opus.organic.smells.ranking.LongParameterListComparator;
import br.pucrio.opus.organic.smells.ranking.MessageChainComparator;
import br.pucrio.opus.organic.smells.ranking.RefusedBequestComparator;
import br.pucrio.opus.organic.smells.ranking.ShotgunSurgeryComparator;
import br.pucrio.opus.organic.smells.ranking.SpaghettiCodeComparator;
import br.pucrio.opus.organic.smells.ranking.SpeculativeGeneralityComparator;

public enum SmellName {
	BrainClass(1, new BrainClassComparator()),
	GodClass(2, new GodClassComparator()),
	ComplexClass(3, new ComplexClassComparator()),
	SpaghettiCode(4, new SpaghettiCodeComparator()),
	BrainMethod(5, new BrainMethodComparator()),
	DispersedCoupling(6, new DispersedCouplingComparator()),
	IntensiveCoupling(7, new IntensiveCouplingComparator()),
	LongMethod(8, new LongMethodComparator()),
	ShotgunSurgery(9, new ShotgunSurgeryComparator()),
	FeatureEnvy(10, new FeatureEnvyComparator()),
	LazyClass(11, new LazyClassComparator()),
	RefusedBequest(12, new RefusedBequestComparator()),
	SpeculativeGenerality(13, new SpeculativeGeneralityComparator()),
	DataClass(14, new DataClassComparator()),
	MessageChain(15, new MessageChainComparator()),
	LongParameterList(16, new LongParameterListComparator()),
	ClassDataShouldBePrivate(17, new ClassDataShouldBePrivateComparator());
	
	private Integer priority;
	
	private Comparator<Smell> comparator;
	
	private SmellName(Integer priority) {
		this.priority = priority;
	}
	
	private SmellName(Integer priority, Comparator<Smell> comparator) {
		this.priority = priority;
		this.comparator = comparator;
	}

	public Integer getPriority() {
		return priority;
	}
	
	public Comparator<Smell> getComparator() {
		return comparator;
	}
}
