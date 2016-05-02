package edu.uta.cse.group9.model;

public enum StudentType {
	UNDERGRADUATE_CSE	(0, "Undergraduate-CSE"),
	GRADUATE_CSE		(1, "Graduate-CSE"),
	DOCTORATE_CSE		(2, "Doctorate-CSE"),
	UNDERGRADUATE_ME	(3, "Undergraduate-ME"),
	GRADUATE_ME		(4, "Graduate-ME"),
	DOCTORATE_ME		(5, "Doctorate-ME"),
	UNDERGRADUATE_EE	(6, "Undergraduate-EE"),
	GRADUATE_EE		(7, "Graduate-EE"),
	DOCTORATE_EE		(8, "Doctorate-EE");
	
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
