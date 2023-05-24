/**

 * 
 */
package com.dropwizard.rest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flipkart.utils.DBUtils;




/**
 * @author harshvardhan.h2
 * 
 *
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloRestController {
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost/test";

	  //  Database credentials
	  static final String USER = "root";
	  static final String PASS = "HarshV@123";
	  Connection conn = null;
	  PreparedStatement stmt = null;

	 @GET
	    public String getHello() {
		 try {
//			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			 conn = DBUtils.getConnection();
		     System.out.println("Creating statement...");
		     String sql="insert into test values(?,?,?,?)";
		     //String sql = "UPDATE Employees set age=? WHERE id=?";
		    // String sql1="delete from employee where id=?";
		    // stmt.setInt(1, 101);
		     stmt = conn.prepareStatement(sql);
		 
		     // Hard coded data
		     
		     int id=645;
		     String name="Vabhav";
		     String address="Delhi";
		     String location="india";
		     //Bind values into the parameters.
		     stmt.setInt(1, id);  // This would set age
		     stmt.setString(2,name);
		     stmt.setString(3, address);
		     stmt.setString(4, location);
		     stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return "my dropwizard service";
	    }

}