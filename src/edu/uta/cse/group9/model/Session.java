package edu.uta.cse.group9.model;

public class Session {
	
	public static final String TABLE_NAME = "user_session";
	
	// Variables
	
	private String token;
	private User user;
	
	// Constructors
	
	public Session(String token, User user) {
		super();
		this.token = token;
		this.user = user;
	}	
	
	// Getters and Setters
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
