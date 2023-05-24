package com.flipkart.bean;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */

public class Card {
	
	String cardType;
	String pin;
	String holderName;
	String yearOfExpiration;
	
	/**
	 * Retrieves the card type.
	 *
	 * @return The card type.
	 */

	public String getCardType() {
		return cardType;
	}
	
	/**
	 * Sets the card type.
	 *
	 * @param cardType The card type to set.
	 */

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	/**
	 * Retrieves the PIN associated with the card.
	 *
	 * @return The PIN.
	 */

	public String getPin() {
		return pin;
	}
	/**
	 * Sets the PIN associated with the card.
	 *
	 * @param pin The PIN to set.
	 */

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	/**
	 * Retrieves the name of the card holder.
	 *
	 * @return The card holder's name.
	 */

	public String getHolderName() {
		return holderName;
	}
	
	/**
	 * Sets the name of the card holder.
	 *
	 * @param holderName The card holder's name to set.
	 */

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	/**
	 * Retrieves the year of expiration for the card.
	 *
	 * @return The year of expiration.
	 */

	public String getYearOfExpiration() {
		return yearOfExpiration;
	}
	
	/**
	 * Sets the year of expiration for the card.
	 *
	 * @param yearOfExpiration The year of expiration to set.
	 */

	public void setYearOfExpiration(String yearOfExpiration) {
		this.yearOfExpiration = yearOfExpiration;
	}
	
	

}
