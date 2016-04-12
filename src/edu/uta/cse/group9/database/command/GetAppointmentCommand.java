package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.sql.Timestamp;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.AppointmentStatus;
import edu.uta.cse.group9.model.Student;

public class GetAppointmentCommand extends SQLCommand {

	// Variables
	
	private Integer id;
	
	// Constructors
	
	public GetAppointmentCommand(Integer id) {
		super();
		this.id = id;
	}

	@Override
	void queryDB() throws Exception {
		if (this.id == null) {
			System.err.println("ID parameter not set.");
			return;
		}
		String query = String.format("SELECT * FROM %s.%s WHERE id = ?", config.DBNAME, Appointment.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
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
				result = appointment;
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
