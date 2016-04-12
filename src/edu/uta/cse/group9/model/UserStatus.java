package edu.uta.cse.group9.model;

public enum UserStatus {
	INACTIVE	(0, "Inactive"),
	ACTIVE	 	(1, "Active");
	
	// Variables
	private final int code;
	private final String description;

	// Constructors
	UserStatus(int code, String description) {
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
	
	public static UserStatus forCode(int code) {
		for (UserStatus s : UserStatus.values()) {
			if (s.code == code) {
				return s;
			}
		}
		return null;
	}
}
