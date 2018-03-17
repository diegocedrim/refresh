package br.pucrio.opus.organic.smells.ranking;

import br.pucrio.opus.organic.collector.Smell;

public class SmellComparatorReverse extends SmellComparator {

	@Override
	public int compare(Smell one, Smell other) {
		int result = super.compare(one, other);
		return -result;
	}

}
