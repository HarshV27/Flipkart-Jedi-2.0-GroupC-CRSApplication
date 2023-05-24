/**
 * 
 */
package com.flipkart.client;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Professor;
import com.flipkart.bean.RegisteredCourse;
import com.flipkart.bean.Student;
import com.flipkart.constant.GenderConstant;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.RoleConstant;
import com.flipkart.exception.CourseExistsAlreadyException;
import com.flipkart.exception.CourseNotDeletedException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.ProfessorNotAddedException;
import com.flipkart.exception.StudentNotFoundForApprovalException;
import com.flipkart.exception.UserIdAlreadyInUseException;
import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.NotificationInterface;
import com.flipkart.service.NotificationOperation;
import com.flipkart.service.RegistrationInterface;
import com.flipkart.service.RegistrationOperation;

/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class AdminCRSMenu {

	AdminInterface adminOperation = AdminOperation.getInstance();
	Scanner in = new Scanner(System.in);
	NotificationInterface notificationInterface=NotificationOperation.getInstance();
	RegistrationInterface registrationInterface = RegistrationOperation.getInstance();
	Map<String, Object> studentInfo = null;
	
	
	/**
	 * Method to Create Admin Menu
	 */
	public void createMenu(){
		
		while(CRSApplication.loggedin) {
			System.out.println("\n╔════════════════════════════════════════╗");
			System.out.println("║               Admin Menu               ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║ 1. View Courses in catalog             ║");
			System.out.println("║ 2. Add Course to catalog               ║");
			System.out.println("║ 3. Delete Course from catalog          ║");
			System.out.println("║ 4. Approve Student Registration        ║");
			System.out.println("║ 5. View Pending Approvals              ║");
			System.out.println("║ 6. Add Professor                       ║");
			System.out.println("║ 7. Assign Professor To Courses         ║");
			System.out.println("║ 8. Generate Report Card                ║");
			System.out.println("║ 9. Logout                              ║");
			System.out.println("╚════════════════════════════════════════╝");
			System.out.print("Choose From Menu: ");

			int choice = in.nextInt();
			
			switch(choice) {
			case 1:
				viewCoursesInCatalogue();
				break;
				
			case 2:
				addCourseToCatalogue();
				break;
				
			case 3:
				removeCourse();
				break;
				
			case 4:
				approveStudent();
				break;
			
			case 5:
				viewPendingAdmissions();
				break;
			
			case 6:
				addProfessor();
				break;
			
			case 7:
				assignCourseToProfessor();
				break;
				
			case 8:
				generateReportCard();
				break;
			
			case 9:
				CRSApplication.loggedin = false;
				return;

			default:
				System.out.println("═════ Wrong Choice ═════");
			}
		}
	}
	
	
	
	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
		List<Course> courseList = adminOperation.viewCourses();
		if(courseList.size() == 0) {
			System.out.println("Nothing present in the catalogue!");
			return courseList;
		}
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         COURSE CATALOGUE                             ║");
		System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║    COURSE CODE            COURSE NAME             INSTRUCTOR         ║");
        courseList.forEach(course -> {
            System.out.println("║ " + String.format("%20s | %20s | %20s", course.getCourseCode(), course.getCourseName(), course.getInstructorId()) + "   ║");
        });

		System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
		return courseList;
	}
	
	/**
	 * Method to add Course to catalogue
	 * @throws CourseExistsAlreadyException 
	 */
	
	
	private void addCourseToCatalogue() {
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            ADD  COURSES                              ║");
		List<Course> courseList = viewCoursesInCatalogue();

		in.nextLine();
		System.out.println("Enter Course Code:");
		String courseCode = in.nextLine();
		
		System.out.println("Enter Course Name:");
		String courseName = in.next();
		
		System.out.println("Enter course Fee:");
		int courseFee = in.nextInt();
		
		Course course = new Course(courseCode, courseName,"", 10, courseFee);
		course.setCourseCode(courseCode);
		course.setCourseName(courseName);
		course.setSeats(10);
		course.setCourseFee(courseFee);
		
		try {
		adminOperation.addCourse(course, courseList);		
		}
		catch (CourseExistsAlreadyException e) {
			System.out.println("Course already existed "+e.getMessage());
		}

	}
	
	
	/**
	 * Method to delete Course from catalogue
	 * @throws CourseNotFoundException 
	 */
	private void removeCourse() {
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                             REMOVE COURSES                           ║");
		List<Course> courseList = viewCoursesInCatalogue();
		System.out.println("Enter Course Code:");
		String courseCode = in.next();
		
		try {
			adminOperation.removeCourse(courseCode, courseList);
			System.out.println("\nCourse Deleted : "+courseCode+"\n");
		} catch (CourseNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
		catch (CourseNotDeletedException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Method to approve a Student using Student's ID
	 */
	private void approveStudent() {

		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0) {
			
			
			return;
		}
		
		int choice = 0;		
		System.out.println("Select the type of approval:\n1. All student 2. By ID.\n");
		choice = in.nextInt();
		
		
		
		if(choice == 1)
		{
			for(Student s : studentList) {
				try {
					adminOperation.approveStudent(s.getUserId(), studentList);
					System.out.println("\nStudent Id : " +s.getUserId()+ " has been approved\n");
			
				} catch (StudentNotFoundForApprovalException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		else {
			System.out.println("Enter Student's ID:");
			String studentUserIdApproval = in.next();
			
			
			try {
				adminOperation.approveStudent(studentUserIdApproval, studentList);
				System.out.println("\nStudent Id : " +studentUserIdApproval+ " has been approved\n");
				//send notification from system
				notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, studentUserIdApproval, null,0);
		
			} catch (StudentNotFoundForApprovalException e) {
				System.out.println(e.getMessage());
			}
		}
	}


	/**
	 * Method to view Students who are yet to be approved
	 * @return List of Students whose admissions are pending
	 */
	
	
	private List<Student> viewPendingAdmissions() {
		
		List<Student> pendingStudentsList= adminOperation.viewPendingAdmissions();
		if(pendingStudentsList.size() == 0) {
			System.out.println("No students pending approvals");
			return pendingStudentsList;
		}
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            PENDING APPROVALS                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║      STUDENT ID                NAME                 GENDER           ║");
        pendingStudentsList.forEach(student -> {
            System.out.println("║ " + String.format("%20s | %20s | %20s", student.getStudentId(), student.getName(), student.getGender().toString()) + "║");
        });

		System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
		return pendingStudentsList;
	}

	
	
	/**
	 * Method to add Professor to DB
	 */
	private void addProfessor() {
		System.out.println("╔════════════════════════════════════════════╗");
		System.out.println("║           PROFESSOR REGISTRATION           ║");
		System.out.println("╠════════════════════════════════════════════╣");
		System.out.println("║ Enter Professor Id:                        ║");
		System.out.print("║    ");
		String userId = in.next();
		Professor professor = new Professor(userId);
		
		System.out.println("║ Enter Professor Name:                      ║");
		System.out.print("║    ");
		String professorName = in.next();
		professor.setName(professorName);
		
		System.out.println("║ Enter Department:                          ║");
		System.out.print("║    ");
		String department = in.next();
		professor.setDepartment(department);
		
		System.out.println("║ Enter Designation:                         ║");
		System.out.print("║    ");
		String designation = in.next();
		professor.setDesignation(designation);
		
		System.out.println("║ Enter Password:                            ║");
		System.out.print("║    ");
		String password = in.next();
		professor.setPassword(password);
		
		System.out.println("║ Gender:        1: Male     2: Female       ║");
		System.out.println("║                3: Other                    ║");
		System.out.print("║    ");
		int gender = in.nextInt();
		
		if(gender==1)
			professor.setGender(GenderConstant.MALE);
		else if(gender==2)
			professor.setGender(GenderConstant.FEMALE);
		else if(gender==3)
			professor.setGender(GenderConstant.OTHER);
		
		System.out.println("║ Enter Address:                             ║");
		System.out.print("║    ");
		String address = in.next();
		professor.setAddress(address);
		
		professor.setRole(RoleConstant.PROFESSOR);
		System.out.println("╚════════════════════════════════════════════╝");
		
		
		try {
			adminOperation.addProfessor(professor);
			System.out.println("Professor added!");
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Assign course to professor
	 */
	
	private void assignCourseToProfessor() {
		List<Professor> professorList= adminOperation.viewProfessors();
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                             PROFESSOR                                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║     PROFESSOR ID              NAME               DESIGNATION         ║");
        professorList.forEach(professor -> {
            System.out.println("║ " + String.format("%20s | %20s | %20s", professor.getUserId(), professor.getName(), professor.getDesignation()) + "  ║");
        });
		System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

		
		System.out.println("\n\n");
		List<Course> courseList= adminOperation.viewCourses();
		System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                               COURSES                                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║     COURSE CODE             COURSE NAME              PROFESSOR ID    ║");
		System.out.println("║ "+String.format("%20s | %20s | %20s", "CourseCode", "CourseName", "ProfessorId")+"   ║");
		courseList.forEach(course -> {
		    System.out.println("║ " + String.format("%20s | %20s | %20s", course.getCourseCode(), course.getCourseName(), course.getInstructorId()) + "   ║");
		});
		System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

		System.out.println("Enter Course Code:");
		String courseCode = in.next();
		
		System.out.println("Enter Professor's User Id:");
		String userId = in.next();
		
		try {
			
			adminOperation.assignCourse(courseCode, userId);
		
		}
		catch(CourseNotFoundException | UserNotFoundException  e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Generate report card of student
	 */
		
	private void generateReportCard() {
	    List<Grade> grade_card = null;
	    boolean isReportGenerated = true;
	    
	    Scanner in = new Scanner(System.in);
	    
	    System.out.println("\nEnter the StudentId for report card generation : ");
	    String studentId = in.next();
	    
	    
	    try {
	    	studentInfo = adminOperation.getStudent(studentId);
	        adminOperation.setGeneratedReportCardTrue(studentId);
	        if (isReportGenerated) {
	            grade_card = registrationInterface.viewGradeCard(studentId);
	            System.out.println("╔══════════════════════════════════════════════════════╗");
	            System.out.println("║                    Report Card                       ║");
	            System.out.println("╠══════════════════════════════════════════════════════╣");
	            System.out.println(String.format("║ Name: %-20s Department: %-13s ║", studentInfo.get("name"), studentInfo.get("department")));
	            System.out.println(String.format("║ Gender: %-20s Grad. Year: %-11s ║", studentInfo.get("gender"), studentInfo.get("gradYear")));
	            
	            System.out.println("╠══════════════════════════════════════════════════════╣");
	            System.out.println("║   COURSE CODE     COURSE NAME     GRADE              ║");
	            
	            if (grade_card.isEmpty()) {
	                System.out.println("║ You have not registered for any courses              ║");
	                System.out.println("╚══════════════════════════════════════════════════════╝");
	                return;
	            }
	            
	            for (Grade obj : grade_card) {
	                System.out.println("║ " + String.format("%-16s %-16s %-16s", obj.getCrsCode(), obj.getCrsName(), obj.getGrade()) + "   ║");
	            }
	        } else {
	            System.out.println("║ Report card has not been generated yet              ║");
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    
	    System.out.println("╚══════════════════════════════════════════════════════╝");
	}

	
}
