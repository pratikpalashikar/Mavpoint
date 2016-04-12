package edu.uta.cse.group9.model;

import java.util.Date;

public class Appointment {

	public static final String TABLE_NAME = "appointment";
	
	// Variables
	
	private Integer id;
	private Advisor advisor;
	private Student student;
	private Date startTime;
	private Date endTime;
	private String studentNotes;
	private String advisorNotes;
	private AdvisingTask task;
	private AppointmentStatus status;
	
	// Constructors
	
	public Appointment(Integer id, Advisor advisor, Student student, Date startTime, Date endTime, String studentNotes, 
			String advisorNotes, AdvisingTask task, AppointmentStatus status) {
		super();
		this.id = id;
		this.advisor = advisor;
		this.student = student;
		this.startTime = startTime;
		this.endTime = endTime;
		this.studentNotes = studentNotes;
		this.advisorNotes = advisorNotes;
		this.task = task;
		this.status = status;
	}

	
	// Getters and Setters
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Advisor getAdvisor() {
		return advisor;
	}
	
	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
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
	
	public String getStudentNotes() {
		return studentNotes;
	}
	
	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}
	
	public String getAdvisorNotes() {
		return advisorNotes;
	}
	
	public void setAdvisorNotes(String advisorNotes) {
		this.advisorNotes = advisorNotes;
	}
	
	public AppointmentStatus getStatus() {
		return status;
	}
	
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}


	public AdvisingTask getTask() {
		return task;
	}


	public void setTask(AdvisingTask task) {
		this.task = task;
	}	
}
