package br.pucrio.opus.smells.tests.dummy;

import java.util.Enumeration;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class Customer {
	
	private Vector _rentals = new Vector();

	public String statement() {
		double totalAmount = 0;
		int renterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record\n";
		while (rentals.hasMoreElements()) {
			Rental each = (Rental) rentals.nextElement();
			double thisAmount = each.getCharge();
			if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
				renterPoints = renterPoints + 2;
			else
				renterPoints++;
			result = result + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
			totalAmount = totalAmount + thisAmount;
		}
		result = result + "Amount: " + String.valueOf(totalAmount) + "\n";
		result = result + "Points: " + String.valueOf(renterPoints);
		return result;
	}

}
