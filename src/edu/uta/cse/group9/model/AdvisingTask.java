package edu.uta.cse.group9.model;

public class AdvisingTask {
	
	public static final String TABLE_NAME = "advising_task";
	
	// Variables
	
	private Integer	id;
	private String	name;
	private Advisor advisor;
	private Integer duration;
	
	// Constructors
	
	public AdvisingTask(Integer id, String name, Integer duration, Advisor advisor) {
		this.id = id;
		this.name = name;
		this.advisor = advisor;
		this.duration = duration;
	}

	
	// Getters and Setters
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
