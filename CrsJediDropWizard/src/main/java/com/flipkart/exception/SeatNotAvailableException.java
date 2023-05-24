package com.flipkart.exception;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class SeatNotAvailableException extends Exception{
	
	private String courseCode;

	/**
	 * Constructor
	 * @param courseCode
	 */
	public SeatNotAvailableException(String courseCode)
	{	
		this.courseCode = courseCode;
	}


	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return  "Seats are not available in : " + courseCode;
	}


}