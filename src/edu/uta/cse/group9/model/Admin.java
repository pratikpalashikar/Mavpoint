package edu.uta.cse.group9.model;

import java.util.Date;

import edu.uta.cse.group9.util.JSPMap;

public class Admin extends User {

	// Constructors

	public Admin() {
		super();
	}
	
	public Admin(Integer id, String email, String username, String passwordHash, String passwordSalt,
			Date passwordExpiration, String firstName, String lastName, String utaId, String telephone,UserStatus status) {
		super(id, email, username, passwordHash, passwordSalt, passwordExpiration, firstName, lastName, utaId,telephone,status);
	}
	
	// Methods
	
	public String getHeader() {
		return JSPMap.ADMIN_NAVBAR;
	}
	
	public String getDashboard() {
		return JSPMap.ADMIN_DASHBOARD;
	}
	
	public String getPreferences() {
		return JSPMap.ADMIN_PREFERENCES;
	}	
	
	public Integer getUserTypeId() {
		return 1;
	}
}
