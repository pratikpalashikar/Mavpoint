package edu.uta.cse.group9.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import edu.uta.cse.group9.util.SecurityUtil;

public abstract class User {

	public static final String TABLE_NAME = "user";
	
	// Variables
	protected Integer id = null;
	protected String email = null;
	protected String username = null;
	protected String passwordHash = null;
	protected String passwordSalt = null;
	protected Date passwordExpiration = null;
	protected UserStatus status = UserStatus.INACTIVE;
	
	protected String firstName = null;
	protected String lastName = null;
	protected String utaId = null;
	protected String telephone = null;
	
	// Constructors
	public User() {
		super(); 
	}
	
	public User(Integer id, String email, String username, String passwordHash, String passwordSalt,
			Date passwordExpiration, String firstName, String lastName, String utaId,String telephone, UserStatus status) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.passwordHash = passwordHash;
		this.passwordSalt = passwordSalt;
		this.passwordExpiration = passwordExpiration;
		this.firstName = firstName;
		this.lastName = lastName;
		this.utaId = utaId;
		this.status = status;
		this.telephone = telephone;
	}
	
	// Methods
	
	public Boolean verifyPassword(String password) {
		try {
			return this.passwordHash.equals(new SecurityUtil().getHash(password, this.passwordSalt));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public abstract String getHeader();
	
	public abstract String getDashboard();
	
	public abstract String getPreferences();

	public abstract Integer getUserTypeId();
	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordSalt() {
		return passwordSalt;
	}
	
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	
	public Date getPasswordExpiration() {
		return passwordExpiration;
	}
	
	public void setPasswordExpiration(Date passwordExpiration) {
		this.passwordExpiration = passwordExpiration;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUtaId() {
		return utaId;
	}
	
	public void setUtaId(String utaId) {
		this.utaId = utaId;
	}

	public UserStatus getStatus() {
		return status;
	}
	
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
}
