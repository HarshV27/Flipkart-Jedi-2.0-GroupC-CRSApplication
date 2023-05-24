/**
 * 
 */
package com.flipkart.restController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Notification;
import com.flipkart.bean.Grade;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.constant.SQLQueriesConstant;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.exception.CourseAlreadyRegisteredException;
import com.flipkart.exception.CourseLimitExceededException;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.SeatNotAvailableException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import com.flipkart.service.RegistrationInterface;
import com.flipkart.service.RegistrationOperation;
import com.flipkart.utils.DBUtils;
import com.flipkart.validator.StudentValidator;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */

@Path("/student")
public class StudentRestApi {
	private final Validator validator;
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost/test";

	  //  Database credentials
	  static final String USER = "root";
	  static final String PASS = "HarshV@123";

    public StudentRestApi(Validator validator) {
        this.validator = validator;
    }
	
	RegistrationInterface registrationInterface = RegistrationOperation.getInstance();
	ProfessorInterface professorInterface = ProfessorOperation.getInstance();
	
	ObjectMapper mapper = new ObjectMapper();
	

	
	
	private static Logger logger = Logger.getLogger(StudentRestApi.class);
	
	/**
	 * Method to handle API request for course registration
	 * @param courseList
	 * @param studentId
	 * @throws ValidationException
	 * @return
	 * @throws SQLException 
	 */
	
	@POST
	@Path("/registerCourses/{c1}/{c2}/{c3}/{c4}/{c5}/{c6}/{studentId}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCourses(
			@PathParam("c1") String c1,
			@PathParam("c2") String c2,
			@PathParam("c3") String c3,
			@PathParam("c4") String c4,
			@PathParam("c5") String c5,
			@PathParam("c6") String c6,
			@NotNull
			@PathParam("studentId") String studentId)	throws ValidationException, SQLException{
						
		try
		{
			List<Course> availableCourseList = registrationInterface.viewCourses(studentId);
			List<String> courseList = new ArrayList<String>();
			
			courseList.add(c1);
			courseList.add(c2);
			courseList.add(c3);
			courseList.add(c4);
			courseList.add(c5);
			courseList.add(c6);
			
			Set<String> hash_set = new HashSet<String>();
			
//			for(String courseCode:courseList) {
//				registrationInterface.checkCourse(courseCode, studentId, availableCourseList);	
//				
//					if(!hash_set.add(courseCode))
//						return Response.status(500).entity("Duplicate value  : "+courseCode).build();
//			}

			for(String courseCode:courseList) {
				if(registrationInterface.addCourse(courseCode,studentId,availableCourseList))
				{
					System.out.println("Course " + courseCode + " registered sucessfully.");
	
				}
				else
				{
					return Response.status(500).entity(" You have already registered for Course : " + courseCode).build();

				}

			}
			registrationInterface.setRegistrationStatus(studentId);
		}
		catch (CourseLimitExceededException | SeatNotAvailableException | CourseNotFoundException e) 
		{
			logger.info(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
					
		
		return Response.status(201).entity( "Registration Successful").build();
		
	}
	


	/**
	 * Handles api request to add a course
	 * @param courseCode
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@PUT
	@Path("/addCourse/{courseCode}/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCourse(
			@NotNull
			
			@PathParam("courseCode") String courseCode,
			@NotNull
			
			@PathParam("studentId") String studentId) throws ValidationException, SQLException{
		
		
//		System.out.println("entered insde addcourse");
		if(registrationInterface.getRegistrationStatus(studentId) == false)
		{	
//			System.out.println("inside if");
			return Response.status(200).entity("Student course registration is pending").build();
		}
		List<Course> availCourseList = registrationInterface.viewCourses(studentId);
		//registrationInterface.checkCourse(courseCode, studentId, availCourseList);	
		try {
			registrationInterface.addCourse(courseCode, studentId,availCourseList);
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseLimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeatNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(201).entity( "You have successfully added Course : " + courseCode).build();
		

	}

	
	/**
	 * Handles API request to drop a course
	 * @param courseCode
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@DELETE
	@Path("/dropCourse/{courseCode}/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dropCourse(
			@NotNull
			
			@PathParam("courseCode") String courseCode,
			@NotNull
			
			@PathParam("studentId") String studentId) throws ValidationException, SQLException{
		
		

		if(registrationInterface.getRegistrationStatus(studentId) == false)
			return Response.status(200).entity("Student course registration is pending").build();
		
		try{
			
			List<Course>registeredCourseList = registrationInterface.viewRegisteredCourses(studentId);
			registrationInterface.dropCourse(courseCode, studentId, registeredCourseList);
			return Response.status(201).entity( "You have successfully dropped Course : " + courseCode).build();
		}
		catch(CourseNotFoundException e)
		{	
			logger.info(e.getMessage());
			return Response.status(501).entity("You have not registered for course : " + e.getCourseCode()).build();
		} 
		
	}
	
	
	/**
	 * Method handles API request to view the list of available courses for a student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@GET
	@Path("/viewAvailableCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewCourse(
			@NotNull
			@QueryParam("studentId") String studentId) throws ValidationException, SQLException{
		
		return registrationInterface.viewCourses(studentId);
		
	}
	
	/**
	 * Method handles API request to view the list of registered courses for a student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@GET
	@Path("/viewRegisteredCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewRegisteredCourse(
			@NotNull
			@QueryParam("studentId") String studentId) throws ValidationException, SQLException{
		
			return registrationInterface.viewRegisteredCourses(studentId);
	}
	
	/**
	 * Method handles API request to make payment for registered courses
	 * @param studentId
	 * @param PaymentModeConstant
	 * @return
	 * @throws ValidationException
	 */
	@POST
	@Path("/make_payment/{studentId}/{PaymentModeConstant}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response make_payment(
			@NotNull
			
			@PathParam("studentId") String studentId , 
			
			@PathParam("PaymentModeConstant") int P) throws ValidationException{
		
		double fee = 0;
		try {
			fee = registrationInterface.calculateFee(studentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			logger.info("Your total fee  = " + fee);
			PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(P);
						
			return Response.status(201).entity("Your total fee  = " + fee+"\n"+"Your Payment is successful\n").build();
		
	}
	
	/**
	 * Method handles request to display the total fees for student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@GET
	@Path("/calculateFees")
	public Response calculateFee(
			@NotNull
			@QueryParam("studentId") String studentId) throws ValidationException, SQLException{
		
			double fee = registrationInterface.calculateFee(studentId);
			return Response.status(200).entity("Your total fee  = " + fee + "\n").build();
	}
	
	/**
	 * Method handles request to display the grade card for student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
		@GET
	@Path("/viewGradeCard")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Grade> viewGradeCard(
			@NotNull
			@QueryParam("studentId") String studentId) throws ValidationException, SQLException{
		

			List<Grade> grade_card = registrationInterface.viewGradeCard(studentId);

			return grade_card;
		
	}
	
}