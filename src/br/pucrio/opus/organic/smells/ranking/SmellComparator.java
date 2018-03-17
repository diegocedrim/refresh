package br.pucrio.opus.organic.smells.ranking;

import java.util.Comparator;

import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.organic.collector.SmellName;

public class SmellComparator implements Comparator<Smell> {

	@Override
	public int compare(Smell one, Smell other) {
		SmellName nameOne = one.getName();
		SmellName nameTwo = other.getName();
		/*
		 * If the smells are different, the Smells priority is taken into account.
		 * In this comparator, we want the smell with lower priority be on the top
		 * of the list. The idea is to produce a comparator that, when used, the less
		 * prioritized smells be on the begining of the list 
		 */
		if (!nameOne.equals(nameTwo)) {
			return -nameOne.getPriority().compareTo(nameTwo.getPriority());
		}
		/*
		 * Otherwise, we need to use the proper smell comparator of the particular
		 * type shared by both instances
		 */
		Comparator<Smell> comparator = nameOne.getComparator(); 
		return comparator.compare(one, other);
	}

}
