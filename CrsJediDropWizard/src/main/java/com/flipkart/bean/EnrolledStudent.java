/**
 * 
 */
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
public class EnrolledStudent {
	String courseCode;
	String courseName;
	String studentId;
	
	/**
	 * Constructor for creating an EnrolledStudent object.
	 *
	 * @param courseCode The course code of the enrolled student.
	 * @param courseName The course name of the enrolled student.
	 * @param studentId The student ID of the enrolled student.
	 */

	public EnrolledStudent(String courseCode,String courseName,String studentId){
		this.courseCode=courseCode;
		this.courseName=courseName;
		this.studentId=studentId;
	}
    
	/**
	 * Retrieves the course code of the enrolled student.
	 *
	 * @return The course code.
	 */

	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * Sets the course code of the enrolled student.
	 *
	 * @param courseCode The course code to set.
	 */

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Retrieves the course name of the enrolled student.
	 *
	 * @return The course name.
	 */

	public String getCourseName() {
		return courseName;
	}
    
	/**
	 * Sets the course name of the enrolled student.
	 *
	 * @param courseName The course name to set.
	 */

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Retrieves the student ID of the enrolled student.
	 *
	 * @return The student ID.
	 */

	public String getStudentId() {
		return studentId;
	}
	/**
	 * Sets the student ID of the enrolled student.
	 *
	 * @param studentId The student ID to set.
	 */

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
}
