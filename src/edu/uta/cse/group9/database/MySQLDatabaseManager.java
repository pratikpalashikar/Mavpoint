package edu.uta.cse.group9.database;

import java.util.Date;
import java.util.List;

import edu.uta.cse.group9.database.command.CancelAppointmentCommand;
import edu.uta.cse.group9.database.command.GetAdvisingTaskCommand;
import edu.uta.cse.group9.database.command.GetAdvisingTasksCommand;
import edu.uta.cse.group9.database.command.GetAdvisorListCommand;
import edu.uta.cse.group9.database.command.GetAppointmentCommand;
import edu.uta.cse.group9.database.command.GetAppointmentsForUserCommand;
import edu.uta.cse.group9.database.command.GetTimeSlotCommand;
import edu.uta.cse.group9.database.command.GetTimeSlotsForAdvisorCommand;
import edu.uta.cse.group9.database.command.GetUserCommand;
import edu.uta.cse.group9.database.command.InsertAppointmentCommand;
import edu.uta.cse.group9.database.command.InsertSessionCommand;
import edu.uta.cse.group9.database.command.InsertTimeSlotCommand;
import edu.uta.cse.group9.database.command.InsertUserCommand;
import edu.uta.cse.group9.database.command.SQLCommand;
import edu.uta.cse.group9.database.command.TerminateSessionCommand;
import edu.uta.cse.group9.database.command.UpdateAppointmentCommand;
import edu.uta.cse.group9.database.command.UpdatePasswordCommand;
import edu.uta.cse.group9.database.command.UpdateTimeSlotCommand;
import edu.uta.cse.group9.database.command.UpdateUserCommand;
import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.Session;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;

public class MySQLDatabaseManager implements DatabaseManagerImpl {

	public boolean terminateSession(String token) throws Exception {
		SQLCommand command = new TerminateSessionCommand(token);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean insertAppointment(Appointment appointment) throws Exception {
		SQLCommand command = new InsertAppointmentCommand(appointment);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean updateAppointment(Appointment appointment) throws Exception {
		SQLCommand command = new UpdateAppointmentCommand(appointment);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean insertSession(Session session) throws Exception {
		SQLCommand command = new InsertSessionCommand(session);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean insertUser(User user) throws Exception {
		SQLCommand command = new InsertUserCommand(user);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean updateUser(User user) throws Exception {
		SQLCommand command = new UpdateUserCommand(user);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public User getUserByToken(String token) throws Exception {
		SQLCommand command = new GetUserCommand(null, token, null);
		command.execute();		
		return (User) command.getResult();
	}
	
	public AdvisingTask getAdvisingTask(Integer id, Advisor advisor) throws Exception {
		SQLCommand command = new GetAdvisingTaskCommand(id, advisor);
		command.execute();
		return (AdvisingTask) command.getResult();
	}

	public List<AdvisingTask> getAdvisingTasks(Advisor advisor) throws Exception {
		SQLCommand command = new GetAdvisingTasksCommand(advisor);
		command.execute();
		return (List<AdvisingTask>) command.getResult();
	}
	
	public User getUserById(Integer id) throws Exception {
		SQLCommand command = new GetUserCommand(id, null, null);
		command.execute();
		return (User) command.getResult();		
	}
	
	public User getUserByUsername(String username) throws Exception {
		SQLCommand command = new GetUserCommand(null, null, username);
		command.execute();
		return (User) command.getResult();
	}
		
	public boolean insertTimeSlot(TimeSlot tS) throws Exception {
		SQLCommand command = new InsertTimeSlotCommand(tS);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public boolean updateTimeSlot(TimeSlot ts) throws Exception {
		SQLCommand command = new UpdateTimeSlotCommand(ts);
		command.execute();
		return (Boolean) command.getResult();
	}
	
	public TimeSlot getTimeSlot(Integer id) throws Exception {
		SQLCommand command = new GetTimeSlotCommand(id);
		command.execute();
		return (TimeSlot) command.getResult();
	}
	
	public List<Advisor> getAdvisorList() throws Exception {
		SQLCommand command = new GetAdvisorListCommand();
		command.execute();
		return (List<Advisor>) command.getResult();
	}
	
	public List<Appointment> getAppointmentsForUser(User user, Date start, Date end) throws Exception {
		SQLCommand command = new GetAppointmentsForUserCommand(user, start, end);
		command.execute();
		return (List<Appointment>) command.getResult();
	}
	
	public Appointment getAppointment(Integer id) throws Exception {
		SQLCommand command = new GetAppointmentCommand(id);
		command.execute();
		return (Appointment) command.getResult();
	}

	public List<TimeSlot> getTimeSlotsForAdvisor(Advisor advisor, Date start, Date end) throws Exception {
		SQLCommand command = new GetTimeSlotsForAdvisorCommand(advisor, start, end);
		command.execute();
		return (List<TimeSlot>) command.getResult();
	}

	@Override
	public boolean updatePassword(User user) throws Exception {
		SQLCommand command = new UpdatePasswordCommand(user);
		command.execute();
		return (Boolean) command.getResult();
	}

	@Override
	public Object cancelScheduledAppointment(TimeSlot timeslot) throws Exception {
		SQLCommand command = new CancelAppointmentCommand(timeslot);
		command.execute();
		return command.getResult();
	}

	
}
