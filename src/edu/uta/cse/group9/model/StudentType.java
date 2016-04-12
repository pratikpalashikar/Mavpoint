package edu.uta.cse.group9.model;

public enum StudentType {
	UNDERGRADUATE	(0, "Undergraduate"),
	GRADUATE		(1, "Graduate"),
	DOCTORATE		(2, "Doctorate");
	
	// Variables
	private final int code;
	private final String description;
	
	// Constructors
	StudentType(int code, String description) {
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
	
	public static StudentType forCode(int code) {
		for (StudentType s : StudentType.values()) {
			if (s.code == code) {
				return s;
			}
		}
		return null;
	}
}
