package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.AppointmentStatus;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;

public class GetAppointmentsForUserCommand extends SQLCommand {

	// Variables
	
	private User user;
	private Timestamp start;
	private Timestamp end;
	
	// Constructors
	
	public GetAppointmentsForUserCommand(User user, Date start, Date end) {
		super();
		this.user = user;
		this.start = start != null ? new Timestamp(start.getTime()) : null;
		this.end = end != null ? new Timestamp(end.getTime()) : null;
	}

	void setupQuery(User user) throws Exception {
		String query = String.format("SELECT * FROM %s.%s WHERE advisor_id = ? OR student_id = ?", config.DBNAME, Appointment.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.user.getId());
			statement.setInt(2, this.user.getId());
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}		
	}

	void setupQuery(User user, Timestamp start, Timestamp end) throws Exception {
		String query = String.format("SELECT * FROM %s.%s WHERE advisor_id = ? OR student_id = ? AND start_time >= ? AND end_time <= ?", 
				config.DBNAME, Appointment.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setInt(2, user.getId());
			statement.setTimestamp(3, start);
			statement.setTimestamp(4, end);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	@Override
	void queryDB() throws Exception {
		if (this.user == null) {
			System.err.println("User parameter not set.");
			return;
		}
		if (start == null && end == null) {
			setupQuery(this.user);
		} else if (start != null && end != null) {
			setupQuery(this.user, this.start, this.end);
		} else {
			// TODO could actually make it work for this case
			System.err.println("One date or other is null.");
			return;
		}
		try {
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		List<Appointment> appointments = new ArrayList<Appointment>();
		try {
			while (resultSet.next()) {	
				DatabaseManager dbmgr = new DatabaseManager();
				Integer id = resultSet.getInt("id");
				Timestamp start = resultSet.getTimestamp("start_time");
				Timestamp end = resultSet.getTimestamp("end_time");
				String studentNotes = resultSet.getString("student_notes");
				String advisorNotes = resultSet.getString("advisor_notes");
				AppointmentStatus status = AppointmentStatus.forCode(resultSet.getInt("status_id"));

				// retrieve advisor object
				Integer advisorId = resultSet.getInt("advisor_id");
				Advisor advisor = (Advisor) dbmgr.getUserById(advisorId);

				// retrieve student object
				Integer studentId = resultSet.getInt("student_id");
				Student student = (Student) dbmgr.getUserById(studentId);

				// retrieve task object
				Integer taskId = resultSet.getInt("task_id");
				AdvisingTask task = dbmgr.getAdvisingTask(taskId, advisor);
				
				Appointment appointment = new Appointment(id, advisor, student, start, end, studentNotes, advisorNotes, task, status);
				appointments.add(appointment);
			}
			result = appointments;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
