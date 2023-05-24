/**
 * 
 */
package com.flipkart.dao;

import java.sql.SQLException;
import java.util.UUID;

import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.PaymentModeConstant;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public interface NotificationDaoInterface {

	/**
	 * Send Notification using SQL commands
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: mode of payment used, defined in enum
	 * @param amount
	 * @return notification id for the record added in the database
	 * @throws SQLException
	 */
	public int sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,int amount) throws SQLException;
}
