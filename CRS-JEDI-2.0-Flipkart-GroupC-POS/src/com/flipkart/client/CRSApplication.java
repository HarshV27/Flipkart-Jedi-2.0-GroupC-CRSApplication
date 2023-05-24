/**
 * 
 */
package com.flipkart.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.flipkart.constant.GenderConstant;
import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.exception.StudentNotRegisteredException;
import com.flipkart.exception.UserNotFoundException;

import com.flipkart.service.NotificationInterface;
import com.flipkart.service.NotificationOperation;


import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;

/**
 * @team Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class CRSApplication {


	static boolean loggedin = false;
	StudentInterface studentInterface=StudentOperation.getInstance();
	UserInterface userInterface =UserOperation.getInstance();
	NotificationInterface notificationInterface=NotificationOperation.getInstance();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CRSApplication crsApplication=new CRSApplication();
		int userInput;	

		createMainMenu();
		userInput=sc.nextInt();
		try
		{
			

		while(userInput!=4)
		{
			switch(userInput)
			{	
				case 1:

					crsApplication.loginUser();
					break;
				case 2:

					crsApplication.registerStudent();
					break;	
				case 3:
					crsApplication.updatePassword();
					break;
				default:
					System.out.println("Invalid Input");
			}
			createMainMenu();
			userInput=sc.nextInt();
		}
		}
		catch(Exception ex)
		{
			System.out.println("Error occured "+ex);
		}
		finally
		{
			sc.close();
		}
	}
	
	/**
	 * Method to Create Main Menu
	 */
	public static void createMainMenu()
	{
		System.out.println("╔════════════════════════════════════════╗");
		System.out.println("║       Welcome to CRS Application       ║");
		System.out.println("╠════════════════════════════════════════╣");
		System.out.println("║ 1. Login                               ║");
		System.out.println("║ 2. Student Registration                ║");
		System.out.println("║ 3. Update password                     ║");
		System.out.println("║ 4. Exit                                ║");
		System.out.println("╚════════════════════════════════════════╝");
		System.out.println("Enter user input");
	}
	
	/**
	 * Method for Login function2ality
	 */
	public void loginUser()
	{
		Scanner in = new Scanner(System.in);

		String userId,password;
		try
		{
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║               Login                    ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║ Email:                                 ║");
			System.out.print("║    ");
			userId = in.next();
			System.out.println("║ Password:                              ║");
			System.out.print("║    ");
			password = in.next();
			System.out.println("╚════════════════════════════════════════╝");

			loggedin = userInterface.verifyCredentials(userId, password);

			if(loggedin)
			{
				 
				 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
				 
				 LocalDateTime myDateObj = LocalDateTime.now();
				   
				 String formattedDate = myDateObj.format(myFormatObj);  
				
				 String role = userInterface.getRole(userId);
				System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
				System.out.println(" 	Welcome "+ userId);
				System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

				switch(role) {
				case "ADMIN":
					System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
					System.out.println("┃  " + String.format("%s Login Successful", formattedDate) + "  ┃");
					System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
					AdminCRSMenu adminMenu = new AdminCRSMenu();
					adminMenu.createMenu();
					break;
				case "PROFESSOR":
					System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
					System.out.println("┃  " + String.format("%s Login Successful", formattedDate) + "  ┃");
					System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
					ProfessorCRSMenu professorMenu=new ProfessorCRSMenu();
					professorMenu.createMenu(userId);
					
					break;
				case "STUDENT":
					
					String studentId = userId;
					boolean isApproved=studentInterface.isApproved(studentId);
					if(isApproved) {
						System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
						System.out.println("┃  " + String.format("%s Login Successful", formattedDate) + "  ┃");
						System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
						StudentCRSMenu studentMenu=new StudentCRSMenu();
						studentMenu.create_menu(studentId);
						
					} else {
						System.out.println("Failed to login, you have not been approved by the administration!");
						loggedin=false;
					}
					break;
				}
				
				
			}
			else
			{
				System.out.println("Invalid Credentials!");
			}
			
		}
		catch(UserNotFoundException ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}
	
	/**
	 * Method to help Student register themselves, pending admin approval
	 */
	public void registerStudent()
	{
		Scanner sc=new Scanner(System.in);

		String userId,name,password,address,branchName;
		GenderConstant gender;
		int genderV, batch;
		try
		{
			//input all the student details
			System.out.println("╔════════════════════════════════════════════╗");
			System.out.println("║            Student Registration            ║");
			System.out.println("╠════════════════════════════════════════════╣");
			System.out.println("║ Name:                                      ║");
			System.out.print("║    ");
			name=sc.nextLine();
			System.out.println("║ Email:                                     ║");
			System.out.print("║    ");
			userId=sc.next();
			System.out.println("║ Password:                                  ║");
			System.out.print("║    ");
			password=sc.next();
			System.out.println("║ Gender:        1: Male     2: Female       ║");
			System.out.println("║                3: Other                    ║");
			System.out.print("║    ");
			genderV=sc.nextInt();
			sc.nextLine();
			

			switch(genderV)
			{
			case 1:
				gender=GenderConstant.MALE;
				break;
			case 2:
				gender=GenderConstant.FEMALE;
				break;
				
			case 3:
				gender=GenderConstant.OTHER;
				break;
			default: 
				gender=GenderConstant.OTHER;
			}
			
			System.out.println("║ Branch:                                  ║");
			System.out.print("║    ");
			branchName=sc.nextLine();
			System.out.println("║ Batch:                                    ║");
			System.out.print("║    ");
			batch=sc.nextInt();
			sc.nextLine();
			System.out.println("║ Address:                                  ║");
			System.out.print("║    ");
			address=sc.nextLine();
			System.out.println("╚════════════════════════════════════════════╝");
			
			String newStudentId = studentInterface.register(name, userId, password, gender, batch, branchName, address);
			

			
		}
		catch(StudentNotRegisteredException ex)
		{
			System.out.println("Something went wrong! "+ex.getStudentName() +" not registered. Please try again");
		}

	}
	
	/**
	 * Method to update password of User
	 */
	public void updatePassword() {
		Scanner in = new Scanner(System.in);
		String userId,newPassword, oldPassword;
		try {
			System.out.println("╔═══════════════════════════════════════════════╗");
			System.out.println("║               Update Password                 ║");
			System.out.println("╠═══════════════════════════════════════════════╣");
			System.out.println("║ Email:                                        ║");
			System.out.print("║    ");
			userId=in.next();
			System.out.println("║ Old Password:                                 ║");
			System.out.print("║    ");
			oldPassword=in.next();
			System.out.println("║ New Password:                                 ║");
			System.out.print("║    ");

			newPassword=in.next();
			System.out.println("╚═══════════════════════════════════════════════╝");

			boolean isUpdated=userInterface.updatePassword(userId, newPassword);
			if(isUpdated)
				System.out.println("Password updated successfully!");

			else
				System.out.println("Something went wrong, please try again!");
		} catch(Exception ex) {
			System.out.println("Error Occured "+ex.getMessage());
		}
		
	}
		
}
