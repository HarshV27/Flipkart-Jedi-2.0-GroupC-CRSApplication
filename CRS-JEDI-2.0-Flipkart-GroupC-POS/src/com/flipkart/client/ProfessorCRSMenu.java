/**
 * 
 */
package com.flipkart.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.flipkart.bean.Course;
import com.flipkart.bean.EnrolledStudent;
import com.flipkart.exception.GradeNotAllotedException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import com.flipkart.validator.ProfessorValidator;


/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class ProfessorCRSMenu {
	
	ProfessorInterface professorInterface = ProfessorOperation.getInstance();

	/**
	 * @param profID
	 */
	public void createMenu(String profID) {
		Scanner in = new Scanner(System.in);
		
		int input;
		while (CRSApplication.loggedin) {
			System.out.println("╔══════════════════════════════╗");
			System.out.println("║        Professor Menu        ║");
			System.out.println("╠══════════════════════════════╣");
			System.out.println("║ 1. View Courses              ║");
			System.out.println("║ 2. View Enrolled Students    ║");
			System.out.println("║ 3. Add Grades                ║");
			System.out.println("║ 4. Logout                    ║");
			System.out.println("╚══════════════════════════════╝");
			System.out.print("Choose From Menu: ");

			input = in.nextInt();
			switch (input) {
			case 1:
				getCourses(profID);
				break;
			case 2:
				viewEnrolledStudents(profID);
				break;
			case 3:
				addGrade(profID);
				break;
			case 4:
				CRSApplication.loggedin = false;
				return;
			default:
				System.out.println("Please select appropriate option...");
			}
		}
		in.close();
	}
	
	/**
	 * View students enrolled in courses of professor 
	 * @param profID
	 */

	public void viewEnrolledStudents(String profID) {
	    System.out.println("╔═════════════════════════════════════════════════════╗");
	    System.out.println("║                  Enrolled Students                  ║");
	    System.out.println("╠═════════════════════════════════════════════════════╣");
	    System.out.println("║   COURSE CODE     COURSE NAME     Student           ║");
	    try {
	        List<EnrolledStudent> enrolledStudents = new ArrayList<>();
	        enrolledStudents = professorInterface.viewEnrolledStudents(profID);
	        enrolledStudents.forEach(obj -> {
	            System.out.println("║ " + String.format("%-16s %-16s %-17s", obj.getCourseCode(), obj.getCourseName(), obj.getStudentId()) + " ║");
	        });

	        
	    } catch (Exception ex) {
	        System.out.println("║ " + ex.getMessage() + "Something went wrong, please try again later! ║");
	    }
	    System.out.println("╚════════════════════════════════════════════════════╝");
	}

	/**
	 * Get courses of professor 
	 * @param profId
	 */
	
	public void getCourses(String profId) {
	    try {
	        List<Course> coursesEnrolled = professorInterface.viewCourses(profId);
	        System.out.println("╔════════════════════════════════════════════════════╗");
	        System.out.println("║                     Courses                        ║");
	        System.out.println("╠════════════════════════════════════════════════════╣");
	        System.out.println("║   COURSE CODE     COURSE NAME     No. of Students  ║");
	        coursesEnrolled.forEach(obj -> {
	            System.out.println("║ " + String.format("%-16s %-16s %-16s", obj.getCourseCode(), obj.getCourseName(), 10 - obj.getSeats()) + " ║");
	        });

	    } catch (Exception ex) {
	        System.out.println("║ Something went wrong! " + ex.getMessage() + " ║");
	    }
	    System.out.println("╚══════════════════════════════════════════════════╝");
	}

	/**
	 * Assign grades to student
	 * @param profId
	 */	
	public void addGrade(String profId) {	
		Scanner in = new Scanner(System.in);
		
		String courseCode, grade, studentId;
		try {
		    List<EnrolledStudent> enrolledStudents = new ArrayList<EnrolledStudent>();
		    enrolledStudents = professorInterface.viewEnrolledStudents(profId);
		    System.out.println("╔════════════════════════════════════════════════════════╗");
		    System.out.println("║                  Enrolled Students                     ║");
		    System.out.println("╠════════════════════════════════════════════════════════╣");
		    System.out.println("║   COURSE CODE     COURSE NAME     Student ID           ║");
		    for (EnrolledStudent obj : enrolledStudents) {
		        System.out.println("║ " + String.format("%-16s %-16s %-20s", obj.getCourseCode(), obj.getCourseName(), obj.getStudentId()) + " ║");
		    }
		    System.out.println("╚════════════════════════════════════════════════════════╝");
			List<Course> coursesEnrolled = new ArrayList<Course>();
			coursesEnrolled	= professorInterface.viewCourses(profId);
			System.out.println("\n═════════════════Add Grade═════════════════");
			System.out.printf("Enter student id: ");
			studentId = in.nextLine();
			System.out.printf("Enter course code: ");
			courseCode = in.nextLine();
			System.out.println("Enter grade: ");
			grade = in.nextLine();
			if (!(ProfessorValidator.isValidStudent(enrolledStudents, studentId)
			&& ProfessorValidator.isValidCourse(coursesEnrolled, courseCode))) {
				professorInterface.addGrade(studentId, courseCode, grade);
				System.out.println("GradeConstant added successfully for "+studentId);
			} else {
				System.out.println("Invalid data entered, try again!");
			}
		} catch(GradeNotAllotedException ex) {
			System.out.println("GradeConstant cannot be added for"+ex.getStudentId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
