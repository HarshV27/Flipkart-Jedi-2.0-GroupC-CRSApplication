package com.flipkart.bean;

import java.util.Date;

import com.flipkart.constant.GenderConstant;
import com.flipkart.constant.RoleConstant;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */

public class Admin extends User
{    /**
	 * Constructor for creating an Admin object
	 * @param userID
	 * @param name
	 * @param gender
	 * @param role
	 * @param password
	 * @param address
	 */

		private String adminID;
	
		public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
		{
			super(userID, name, role, password, gender, address);
		}	
	
}
