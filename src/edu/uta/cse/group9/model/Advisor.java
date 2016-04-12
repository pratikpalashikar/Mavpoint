package edu.uta.cse.group9.model;

import java.util.Date;

import edu.uta.cse.group9.util.JSPMap;

public class Advisor extends User {

	public static final String TABLE_NAME = "advisor";
	
	// Variables
	
	private Boolean emailNotification = true;
	private Boolean assignedStudentRange = false;
	private String startStudent;
	private String endStudent;
	
	// Constructors
	
	public Advisor() {
		super();
	}
	
	public Advisor(Integer id, String email, String username, String passwordHash, String passwordSalt,
			Date passwordExpiration, String firstName, String lastName, String utaId, Boolean emailNotification, Boolean assignedStudent,
			String startStudent, String endStudent,String telephone, UserStatus status) {
		super(id, email, username, passwordHash, passwordSalt, passwordExpiration, firstName, lastName, utaId,telephone, status);
		this.setEmailNotification(emailNotification);
		this.assignedStudentRange = assignedStudent;
		this.startStudent = startStudent;
		this.endStudent = endStudent;
	}
	
	// Methods
	
	public String getHeader() {
		return JSPMap.ADVISOR_NAVBAR;
	}
	
	public String getDashboard() {
		return JSPMap.ADVISOR_DASHBOARD;
	}

	public String getPreferences() {
		return JSPMap.ADVISOR_PREFERENCES;
	}
	
	public Integer getUserTypeId() {
		return 2;
	}

	// Getters and Setters
	
	public Boolean hasEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public Boolean isAssignedStudentRange() {
		return assignedStudentRange;
	}

	public void setAssignedStudentRange(Boolean assignedStudentRange) {
		this.assignedStudentRange = assignedStudentRange;
	}

	public String getStartStudent() {
		return startStudent;
	}

	public void setStartStudent(String startStudent) {
		this.startStudent = startStudent;
	}

	public String getEndStudent() {
		return endStudent;
	}

	public void setEndStudent(String endStudent) {
		this.endStudent = endStudent;
	}
}
