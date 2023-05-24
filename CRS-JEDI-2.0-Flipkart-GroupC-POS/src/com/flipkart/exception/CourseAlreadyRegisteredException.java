/**
 * 
 */
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
public class CourseAlreadyRegisteredException extends Exception{
	
	private String courseCode;
	
	/**
	 * Constructor
	 * @param courseCode
	 */
	public CourseAlreadyRegisteredException(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * Getter method
	 * @return course code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "You have already registered for " + courseCode;
	}
}
