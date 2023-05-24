/**
 * 
 */
package com.flipkart.client;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.SeatNotAvailableException;
import com.flipkart.service.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class StudentCRSMenu {
	
	Scanner sc = new Scanner(System.in);
	RegistrationInterface registrationInterface = RegistrationOperation.getInstance();
	AdminInterface adminInterface = AdminOperation.getInstance();
	ProfessorInterface professorInterface = ProfessorOperation.getInstance();
	NotificationInterface notificationInterface = NotificationOperation.getInstance();
	private boolean is_registered;
	

	
	public void create_menu(String studentId) {
		
		is_registered = getRegistrationStatus(studentId);
		
		while(CRSApplication.loggedin) {
			
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║             Student Menu               ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║ 1. Register for Course                 ║");
			System.out.println("║ 2. Add Course                          ║");
			System.out.println("║ 3. Drop Course                         ║");
			System.out.println("║ 4. View Course                         ║");
			System.out.println("║ 5. View Registered Courses             ║");
			System.out.println("║ 6. View Grade Card                     ║");
			System.out.println("║ 7. Make Payment                        ║");
			System.out.println("║ 8. Logout                              ║");
			System.out.println("╚════════════════════════════════════════╝");
			System.out.print("Choose From Menu: ");

				int choice = sc.nextInt();
			
				switch (choice) {
				
				case 1: 
					registerCourses(studentId);
					break;
					
				case 2:
					addCourse(studentId);
					break;
					
				case 3:
					dropCourse(studentId);
					break;
					
				case 4:
					viewCourse(studentId);
					break;
					
				case 5:
					viewRegisteredCourse(studentId);
					break;
					
				case 6:
					viewGradeCard(studentId);
					break;
					
				case 7:
					make_payment(studentId);
					break;
					
				case 8:
					CRSApplication.loggedin = false;
					break;			
					
				default:
					System.out.println("Incorrect Choice!");
		
		
			}
			
		}
		
	}



/**
 * Add course during registration  
 * @param studentId
 */
private void registerCourses(String studentId)
{
	
	
	if(is_registered)
	{
		System.out.println(" Registration is already completed");
		return;
	}
	
	int count = 0;
	while(count < 6)
	{
		try
		{
			if(count==4) {
				System.out.println("");
		        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		        System.out.println("┃   Now Register for any 2 alternate courses   ┃");
		        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

			}
			List<Course> courseList=viewCourse(studentId);
			
			if(courseList==null)
				return;
			
			System.out.println("Enter Course Code : " + (count+1));
			String courseCode = sc.next();
			
			if(registrationInterface.addCourse(courseCode,studentId,courseList))
			{
				System.out.println("Course " + courseCode + " registered sucessfully.");
				count++;
			}
			else
			{
				System.out.println(" You have already registered for Course : " + courseCode);
			}
		}	
		catch(CourseNotFoundException | CourseLimitExceededException | SQLException e)
		{
			System.out.println(e.getMessage());
		} catch (SeatNotAvailableException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	System.out.println("\n════════════════════════════════════════════════════════");
	System.out.println("Registration Successful");
	System.out.println("════════════════════════════════════════════════════════\n");
	
	try {
		registrationInterface.setRegistrationStatus(studentId);
	}
	catch(SQLException e)
	{
		System.out.println(e.getMessage());
	}
    is_registered = true;
    
}

/**
 * Add course during registration  
 * @param studentId
 */
private void addCourse(String studentId) {
	if(is_registered)
	{
		List<Course> availableCourseList=viewCourse(studentId);
		
		if(availableCourseList==null)
			return;

		try
		{
			System.out.println("Enter Course Code : " );
			String courseCode = sc.next();
			if(registrationInterface.addCourse(courseCode, studentId,availableCourseList))
			{
				System.out.println(" You have successfully registered for Course : " + courseCode);
			}
			else
			{
				System.out.println(" You have already registered for Course : " + courseCode);
			}
		}
		 catch(SQLException e)
		{
			System.out.println(e.getMessage());
		} catch (CourseNotFoundException e) {
			System.out.println(e.getMessage());
			
		} catch (CourseLimitExceededException e) {
			System.out.println(e.getMessage());
			
		} catch (SeatNotAvailableException e) {
			System.out.println(e.getMessage());
			
		}
	}
	else 
	{
		System.out.println("Please complete registration");
	}

}

/**
 * Method to check if student is already registered
 * @param studentId
 * @return Registration Status
 */
private boolean getRegistrationStatus(String studentId)
{
	try 
	{
		return registrationInterface.getRegistrationStatus(studentId);
	} 
	catch (SQLException e)
	{
		System.out.println(e.getMessage());
	}
	return false;
}


/**
 * Drop Course
 * @param studentId
 */
private void dropCourse(String studentId) {
	if(is_registered)
	{
		List<Course> registeredCourseList=viewRegisteredCourse(studentId);
		
		if(registeredCourseList==null)
			return;
		
		System.out.println("Enter the Course Code : ");
		String courseCode = sc.next();
		
		try
		{
			registrationInterface.dropCourse(courseCode, studentId,registeredCourseList);
			System.out.println("You have successfully dropped Course : " + courseCode);
			
		}
		catch(CourseNotFoundException e)
		{
			System.out.println("You have not registered for course : " + e.getCourseCode());
		} 
		catch (SQLException e) 
		{

			System.out.println(e.getMessage());
		}
	}
	else
	{
		System.out.println("Please complete registration");
	}
}


/**
 * View all available Courses 
 * @param studentId
 * @return List of available Courses 
 */
private List<Course> viewCourse(String studentId) {
    List<Course> course_available = null;

    try {
        course_available = registrationInterface.viewCourses(studentId);
    } catch (SQLException e) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                ERROR                   ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ " + e.getMessage() + " ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    if (course_available.isEmpty()) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         NO COURSE AVAILABLE            ║");
        System.out.println("╚════════════════════════════════════════╝");
        return null;
    }

    System.out.println("╔═════════════════════════════════════════════════════════╗");
    System.out.println("║                     Course Catalog                      ║");
    System.out.println("╠═════════════════════════════════════════════════════════╣");
    System.out.println("║ COURSE CODE      COURSE NAME      INSTRUCTOR     SEATS  ║");
    for (Course obj : course_available) {
        System.out.println("║ " + String.format("%-16s %-16s %-16s %-5s", obj.getCourseCode(), obj.getCourseName(), obj.getInstructorId(), obj.getSeats()) + "║");
    }
    System.out.println("╚═════════════════════════════════════════════════════════╝");

    return course_available;
}


/**
 * View Registered Courses
 * @param studentId
 * @return List of Registered Courses
 */
private List<Course> viewRegisteredCourse(String studentId) {
    List<Course> course_registered = null;
    try {
        course_registered = registrationInterface.viewRegisteredCourses(studentId);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    
    if (course_registered.isEmpty()) {
        System.out.println("You haven't registered for any course");
        return null;
    }
    
    System.out.println("╔═════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                      Registered Courses                             ║");
    System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
    System.out.println("║   COURSE CODE     COURSE NAME     INSTRUCTOR     COURSE FEE         ║");
    
    for (Course obj : course_registered) {
        System.out.println("║ " + String.format("%-16s %-16s %-16s %-16s", obj.getCourseCode(), obj.getCourseName(), obj.getInstructorId(), obj.getCourseFee()) + " ║");
    }
    
    System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
    
    return course_registered;
}


/**
 * View grade card for particular student  
 * @param studentId
 */
private void viewGradeCard(String studentId) {
    List<Grade> grade_card = null;
    boolean isReportGenerated = false;
    Map<String, Object> studentInfo = null;

    try {
    	studentInfo = adminInterface.getStudent(studentId);
    	
        isReportGenerated = registrationInterface.isReportGenerated(studentId);
        if (isReportGenerated) {
            grade_card = registrationInterface.viewGradeCard(studentId);
            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.println("║                    Grade Card                        ║");
            System.out.println(String.format("║ Name: %-20s Department: %-13s ║", studentInfo.get("name"), studentInfo.get("department")));
            System.out.println(String.format("║ Gender: %-20s Grad. Year: %-11s ║", studentInfo.get("gender"), studentInfo.get("gradYear")));
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.println("║   COURSE CODE     COURSE NAME     GRADE              ║");
            
            if (grade_card.isEmpty()) {
                System.out.println("║ You haven't registered for any course                ║");
                System.out.println("╚══════════════════════════════════════════════════════╝");
                return;
            }
            
            for (Grade obj : grade_card) {
                System.out.println("║   " + String.format("%-16s %-16s %-16s", obj.getCrsCode(), obj.getCrsName(), obj.getGrade()) + " ║");
            }
        } else {
            System.out.println("║ Report card not yet generated                         ║");
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    
    System.out.println("╚══════════════════════════════════════════════════════╝");
}

/**
 * Make payment for particular student 
 * @param studentId
 */
private void make_payment(String studentId)
{
	
	int fee = 0;
	boolean isreg = false;
	boolean ispaid = false;
	try
	{
		isreg = registrationInterface.getRegistrationStatus(studentId);
		ispaid = registrationInterface.getPaymentStatus(studentId);
	} 
	catch (SQLException e) 
	{
        System.out.println(e.getMessage());
	}

	
	if(!isreg)
	{
		System.out.println("You have not registered yet");
	}
	else if(isreg && !ispaid)
	{
		
		List<Course> regCourse = viewRegisteredCourse(studentId);
		for(Course course: regCourse) {
			fee+=course.getCourseFee();
		}
		
		System.out.println("Your total fee  = " + fee);
		
		System.out.println("Want to continue Fee Payment(y/n)");
		String ch = sc.next();
		if(ch.equals("y"))
		{
			System.out.println("Select Mode of Payment:");
			
			int index = 1;
			for(PaymentModeConstant mode : PaymentModeConstant.values())
			{
				System.out.println(index + " " + mode);
				index = index + 1;
			}
			
			PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(sc.nextInt());
			
			if(mode == null)
				System.out.println("Invalid Input");
			else
			{
				try 
				{	
					if(mode == PaymentModeConstant.CREDIT_CARD) {
						String bankName;
						System.out.println("Enter bank name: ");
						bankName = sc.next();
						String  cardNumber;
						System.out.println("Enter card number: ");
						cardNumber = sc.next();
						String pin;
				        System.out.println("Enter pin: ");
						pin = sc.next();
						
					}else if(mode == PaymentModeConstant.DEBIT_CARD) {
						String bankName;
						System.out.println("Enter bank name: ");
						bankName = sc.next();
						String  cardNumber;
						System.out.println("Enter card number: ");
						cardNumber = sc.next();
						String pin;
				        System.out.println("Enter pin: ");
						pin = sc.next();

					}else if(mode == PaymentModeConstant.NET_BANKING) {
						String bankName;
						System.out.println("Enter bank name: ");
						bankName = sc.next();
						String  username;
						System.out.println("Enter username: ");
						username = sc.next();
						String pin;
				        System.out.println("Enter pin: ");
						pin = sc.next();

					}else if(mode == PaymentModeConstant.SCHOLARSHIP) {
						String scholarshipName;
						System.out.println("Enter scholarship name: ");
						scholarshipName = sc.next();
					}
					else {
						String  username;
						System.out.println("Enter name: ");
						username = sc.next();
					}
					notificationInterface.sendNotification(NotificationTypeConstant.PAYED, studentId, mode, fee);
					
					System.out.println("Payment Successful by StudentId :" + studentId);
					registrationInterface.setPaymentStatus(studentId);				
				}
				catch (Exception e) 
				{

		            System.out.println(e.getMessage());
				}
			}	
		}
		
	}
	
	else
	{
		System.out.println("You have already paid the fees");
	}
	
}
}