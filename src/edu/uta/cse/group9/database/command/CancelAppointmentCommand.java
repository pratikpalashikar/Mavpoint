package edu.uta.cse.group9.database.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;

public class CancelAppointmentCommand extends SQLCommand {

	private TimeSlot appointment;

	public CancelAppointmentCommand(TimeSlot appointment) {
		this.appointment = appointment;
	}

	@Override
	void queryDB() throws Exception {

		List<Integer> studentId = new ArrayList<>();
		ResultSet rs = null, emailRs = null;
		List<String> studentEmail = new ArrayList<>();


		String query = String.format(
				"UPDATE %s.%s SET status_id=?, advisor_notes=? "
						+ " WHERE advisor_id=? and start_time >= ? and end_time<= ?",
				config.DBNAME, Appointment.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, 2);
			statement.setString(2, "Rescheduled Appointment by Advisor");
			statement.setInt(3, this.appointment.getAdvisor().getId());
			statement.setTimestamp(4, new Timestamp(this.appointment.getStartTime().getTime()));
			statement.setTimestamp(5, new Timestamp(this.appointment.getEndTime().getTime()));
			statement.executeUpdate();
			result = true;

			if ((boolean) result) {

				String queryStudentId = String.format(
						"SELECT student_id FROM %s.%s " + " WHERE advisor_id=? and start_time >= ? and end_time<= ?",
						config.DBNAME, Appointment.TABLE_NAME);
				statement = connection.prepareStatement(queryStudentId);

				statement.setInt(1, this.appointment.getAdvisor().getId());
				statement.setTimestamp(2, new Timestamp(this.appointment.getStartTime().getTime()));
				statement.setTimestamp(3, new Timestamp(this.appointment.getEndTime().getTime()));
				rs = statement.executeQuery();
				while (rs.next()) {
					int student_id = rs.getInt("student_id");
					studentId.add(student_id);
				}
			}

		//	if (!studentId.isEmpty()) {

				
				
				for (int i : studentId) {
					String queryStudentEmail = String.format("SELECT email FROM %s.%s " + " WHERE id=? ", config.DBNAME,
							User.TABLE_NAME);
					statement = connection.prepareStatement(queryStudentEmail);
					statement.setInt(1, i);
					emailRs = statement.executeQuery();
					if(emailRs.next())
					studentEmail.add(emailRs.getString("email"));
				}

		//	}

			result = studentEmail;

		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() {
	}
}
