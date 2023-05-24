package com.flipkart.constant;

/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public enum PaymentModeConstant {
	
	CREDIT_CARD,NET_BANKING,DEBIT_CARD,CASH, SCHOLARSHIP;
	
	/**
	 * Method to get Mode of Payment
	 * @param value
	 * @return Mode of Payment
	 */
	public static PaymentModeConstant getPaymentMode(int value)
	{
		switch(value)
		{
			case 1:
				return PaymentModeConstant.CREDIT_CARD;
			case 2:
				return PaymentModeConstant.NET_BANKING;
			case 3:
				return PaymentModeConstant.DEBIT_CARD;
			case 4:
				return PaymentModeConstant.CASH;
			case 5:
				return PaymentModeConstant.SCHOLARSHIP;
			default:
				return null;
				
		}
			
	}
	
}