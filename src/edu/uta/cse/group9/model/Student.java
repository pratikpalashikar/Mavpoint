package edu.uta.cse.group9.model;

import java.util.Date;

import edu.uta.cse.group9.util.JSPMap;
public class Student extends User {
	
	public static final String TABLE_NAME = "student";
	
	// Variables
	
	private EnrollmentStatus enrollmentStatus = null;
	private StudentType	studentType = null;
	
	// Constructors
	public Student() {
		super();
	}
	
	public Student(Integer id, String email, String username, String passwordHash, String passwordSalt,
			Date passwordExpiration, String firstName, String lastName, String utaId, EnrollmentStatus enrollmentStatus,
			StudentType studentType,String telephone, UserStatus status) {
		super(id, email, username, passwordHash, passwordSalt, passwordExpiration, firstName, lastName, utaId,telephone, status);
		this.enrollmentStatus = enrollmentStatus;
		this.studentType = studentType;
	}

	// Methods
	
	public String getHeader() {
		return JSPMap.STUDENT_NAVBAR;
	}

	public String getDashboard() {
		return JSPMap.STUDENT_DASHBOARD;
	}
	
	public String getPreferences() {
		return JSPMap.STUDENT_PREFERENCES;
	}	

	public Integer getUserTypeId() {
		return 3;
	}

	// Getters and Setters
	
	public EnrollmentStatus getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}
	
	public StudentType getStudentType() {
		return studentType;
	}
	
	public void setStudentType(StudentType studentType) {
		this.studentType = studentType;
	}
}
