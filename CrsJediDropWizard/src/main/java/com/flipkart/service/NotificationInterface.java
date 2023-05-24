/**
 *
 */
package com.flipkart.service;


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
public interface NotificationInterface {

    /**
     * Method to send notification
     * @param type: type of the notification to be sent
     * @param studentId: student to be notified
     * @param modeOfPayment: payment mode used
     * @return notification id for the record added in the database
     */


    public int sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,int amount);

}