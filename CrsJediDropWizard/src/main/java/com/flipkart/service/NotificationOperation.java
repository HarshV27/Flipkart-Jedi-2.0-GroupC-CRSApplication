/**
 * 
 */
package com.flipkart.service;

import java.sql.SQLException;
import java.util.UUID;

import com.flipkart.constant.NotificationTypeConstant;
import com.flipkart.constant.PaymentModeConstant;
import com.flipkart.dao.NotificationDaoInterface;
import com.flipkart.dao.NotificationDaoOperation;

/**
 * @author Group C
 *	Isha (Group Leader)
 *	Harshvardhan
 *	Prashant
 *	Ratnesh
 *	Shubh
 *	Lavi
 */
public class NotificationOperation implements NotificationInterface{
	
	private static volatile NotificationOperation instance=null;
	NotificationDaoInterface notificationDaoInterface=NotificationDaoOperation.getInstance();
	private NotificationOperation() {}
	
	/**
	 * Method to make NotificationDaoOperation Singleton
	 * @return
	 */
	public static NotificationOperation getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(NotificationOperation.class){
				instance=new NotificationOperation();
			}
		}
		return instance;
	}
	
	/**
	 * Method to send notification
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: payment mode used
	 * @return notification id for the record added in the database
	 */
	
    @Override
    public int sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,int amount) {
    	try {
			notificationDaoInterface.sendNotification(type, studentId, modeOfPayment, amount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }

	
}