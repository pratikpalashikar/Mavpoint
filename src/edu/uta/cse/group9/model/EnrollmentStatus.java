package edu.uta.cse.group9.model;

public enum EnrollmentStatus {
	PROSPECTIVE	(0, "Prospective"),
	CURRENT		(1, "Current"),
	ALUMNI		(2, "Alumni");
	
	// Variables
	private final int code;
	private final String description;
	
	// Constructors
	EnrollmentStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	// Getters
	public int code() {
		return this.code;
	}
	
	public String description() {
		return this.description;
	}
	
	public static EnrollmentStatus forCode(int code) {
		for (EnrollmentStatus s : EnrollmentStatus.values()) {
			if (s.code == code) {
				return s;
			}
		}
		return null;
	}
}
