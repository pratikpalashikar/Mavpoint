package edu.uta.cse.group9.database;

import java.util.Date;
import java.util.List;

import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.Session;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;

public interface DatabaseManagerImpl {
	// TODO Add methods here and implement them in the MySQLDatabaseManager class
	public boolean terminateSession(String token) throws Exception;
	public boolean insertAppointment(Appointment appointment) throws Exception;
	public boolean updateAppointment(Appointment appointment) throws Exception;
	public boolean insertSession(Session session) throws Exception;
	public boolean insertUser(User user) throws Exception;
	public boolean updateUser(User user) throws Exception;
	public AdvisingTask getAdvisingTask(Integer id, Advisor advisor) throws Exception;
	public List<AdvisingTask> getAdvisingTasks(Advisor advisor) throws Exception;
	public User getUserById(Integer id) throws Exception;
	public User getUserByUsername(String username) throws Exception;
	public User getUserByToken(String token) throws Exception;
	public List<Advisor> getAdvisorList() throws Exception;
	public List<Appointment> getAppointmentsForUser(User advisor, Date start, Date end) throws Exception;
	public Appointment getAppointment(Integer id) throws Exception;
	public List<TimeSlot> getTimeSlotsForAdvisor(Advisor advisor, Date start, Date end)throws Exception;
	public boolean insertTimeSlot(TimeSlot tS) throws Exception;
	public boolean updateTimeSlot(TimeSlot ts) throws Exception;
	public TimeSlot getTimeSlot(Integer id) throws Exception;
	public boolean updatePassword(User user) throws Exception;
	public Object cancelScheduledAppointment(TimeSlot timeslot)throws Exception;
}
