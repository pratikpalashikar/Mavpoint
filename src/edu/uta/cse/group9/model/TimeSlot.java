package edu.uta.cse.group9.model;

import java.util.Date;

public class TimeSlot {

	public static final String TABLE_NAME = "timeslot";
	
	// Variables
	
	private int id;
	private Advisor advisor;
	private Date startTime;
	private Date endTime;

	// Constructor
	
	public TimeSlot(Advisor advisor, Date startTime, Date endTime) {
		super();
		
		this.advisor = advisor;	
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	// Getters and Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	
}