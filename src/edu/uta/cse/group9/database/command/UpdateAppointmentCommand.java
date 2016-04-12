package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.sql.Timestamp;

import edu.uta.cse.group9.model.Appointment;

public class UpdateAppointmentCommand extends SQLCommand {

	private Appointment appointment;
	
	public UpdateAppointmentCommand(Appointment appointment){
		this.appointment = appointment;
	}
	
	@Override
	void queryDB() throws Exception {
		String query = String.format("UPDATE %s.%s SET advisor_id=?, student_id=?, start_time=?, end_time=?, student_notes=?, advisor_notes=?, status_id=?, task_id=?"
				+ " WHERE id=?", config.DBNAME, Appointment.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.appointment.getAdvisor().getId());
			statement.setInt(2, this.appointment.getStudent().getId());
			statement.setTimestamp(3, new Timestamp(this.appointment.getStartTime().getTime()));
			statement.setTimestamp(4, new Timestamp(this.appointment.getEndTime().getTime()));
			statement.setString(5, this.appointment.getStudentNotes());
			statement.setString(6, this.appointment.getAdvisorNotes());
			statement.setInt(7, this.appointment.getStatus().code());
			statement.setInt(8, this.appointment.getTask().getId());
			statement.setInt(9, this.appointment.getId());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() {	}
}
