package com.flipkart.exception;

/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class ProfessorIdAlreadyInUseException extends Exception{
	private String ProfessorId;
	
	
	/***
	 * Setter function for ProfessorId
	 * @param userId
	 */
	
	public ProfessorIdAlreadyInUseException(String id) {
		ProfessorId = id;
	}
	
	/***
	 * Getter function for ProfessorId
	 * @param userId
	 */
	
	public String getUserId() {
		return ProfessorId;
	}
	
	
	@Override
	public String getMessage() {
		return "userId: " + ProfessorId + " is already in use.";
	}

}