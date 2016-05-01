package edu.uta.cse.group9.database;

import java.util.Date;
import java.util.List;

import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.Session;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;

public class DatabaseManager {

	private DatabaseManagerImpl imp;

	public DatabaseManager() {
		imp = new MySQLDatabaseManager();
	}
	
	public boolean terminateSession(String token) throws Exception {
		return imp.terminateSession(token);
	}
	
	public boolean insertAppointment(Appointment appointment) throws Exception {
		return imp.insertAppointment(appointment);
	}
	
	public boolean updateAppointment(Appointment appointment) throws Exception {
		return imp.updateAppointment(appointment);
	}
	
	public boolean insertSession(Session session) throws Exception {
		return imp.insertSession(session);
	}
	
	public boolean insertUser(User user) throws Exception {
		return imp.insertUser(user);
	}
	
	public boolean updatePassword(User user) throws Exception{
		return imp.updatePassword(user);
	}
	
	public boolean updateUser(User user) throws Exception {
		return imp.updateUser(user);
	}
	
	public boolean insertTimeSlot(TimeSlot tS) throws Exception {
		return imp.insertTimeSlot(tS);
	}

	public boolean updateTimeSlot(TimeSlot ts) throws Exception {
		return imp.updateTimeSlot(ts);
	}
	
	public TimeSlot getTimeSlot(Integer id) throws Exception {
		return imp.getTimeSlot(id);
	}
	
	public AdvisingTask getAdvisingTask(Integer id, Advisor advisor) throws Exception {
		return imp.getAdvisingTask(id, advisor);
	}
	
	public List<AdvisingTask> getAdvisingTasks(Advisor advisor) throws Exception {
		return imp.getAdvisingTasks(advisor);
	}
	
	public User getUserById(Integer id) throws Exception {
		return imp.getUserById(id);
	}
	
	public User getUserByUsername(String username) throws Exception {
		return imp.getUserByUsername(username);
	}
	
	public User getUserByToken(String token) throws Exception {
		return imp.getUserByToken(token);
	}
	
	public List<Advisor> getAdvisorList() throws Exception {
		return imp.getAdvisorList();
	}
	
	public List<TimeSlot> getTimeSlotsForAdvisor(Advisor advisor, Date start, Date end) throws Exception {
		return imp.getTimeSlotsForAdvisor(advisor, start, end);
	}

	public List<Appointment> getAppointmentsForUser(User user, Date start, Date end) throws Exception {
		return imp.getAppointmentsForUser(user, start, end);
	}
	
	public Appointment getAppointment(Integer id) throws Exception {
		return imp.getAppointment(id);
	}
}