package edu.uta.cse.group9.model;

public enum AppointmentStatus {
	SCHEDULED	(0, "Scheduled"),
	COMPLETE 	(1, "Complete"),
	CANCELLED	(2, "Canceled");
	
	// Variables
	private final int code;
	private final String description;

	// Constructors
	AppointmentStatus(int code, String description) {
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
	
	public static AppointmentStatus forCode(int code) {
		for (AppointmentStatus s : AppointmentStatus.values()) {
			if (s.code == code) {
				return s;
			}
		}
		return null;
	}
}
