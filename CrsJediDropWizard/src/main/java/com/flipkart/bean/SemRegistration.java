package com.flipkart.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SemRegistration class represents the registration details for a semester.
 *
 * @author Group C
 *    Isha (Group Leader)
 *    Harshvardhan
 *    Prashant
 *    Ratnesh
 *    Shubh
 *    Lavi
 */
public class SemRegistration {
    
    int studenID;
    int sem;
    Date date;
    List<Course> courseList = new ArrayList<Course>();

    /**
     * Constructor for creating a SemRegistration object with student ID, semester, and date.
     *
     * @param studentID The student ID for the semester registration.
     * @param sem The semester for the registration.
     * @param date The date of the registration.
     */
    public SemRegistration(int studentID, int sem, Date date) {
        this.studenID = studentID;
        this.sem = sem;
        this.date = date;
    }

    /**
     * Default constructor for SemRegistration.
     */
    public SemRegistration() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves the student ID for the semester registration.
     *
     * @return The student ID.
     */
    public int getStudenID() {
        return studenID;
    }

    /**
     * Sets the student ID for the semester registration.
     *
     * @param studenID The student ID to set.
     */
    public void setStudenID(int studenID) {
        this.studenID = studenID;
    }

    /**
     * Retrieves the semester for the registration.
     *
     * @return The semester.
     */
    public int getSem() {
        return sem;
    }

    /**
     * Sets the semester for the registration.
     *
     * @param sem The semester to set.
     */
    public void setSem(int sem) {
        this.sem = sem;
    }

    /**
     * Retrieves the date of the registration.
     *
     * @return The registration date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the registration.
     *
     * @param date The registration date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Retrieves the list of courses for the semester registration.
     *
     * @return The list of courses.
     */
    public List<Course> getCourseList() {
        return courseList;
    }

    /**
     * Sets the list of courses for the semester registration.
     *
     * @param courseList The list of courses to set.
     */
    public void setCourseList(List<Course> courseList) {
        this.courseList = new ArrayList<Course>(courseList);
    }
}
