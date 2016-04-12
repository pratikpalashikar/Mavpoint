package edu.uta.cse.group9.database.command;

import java.lang.reflect.Method;
import java.sql.SQLException;

import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;

public class UpdateUserCommand extends SQLCommand {
	
	private User user;
	
	public UpdateUserCommand(User user){
		this.user = user;
	}
	
	@Override
	void queryDB() throws Exception {
		String query = String.format(
				"UPDATE %s.%s SET email=?, username=?, password_hash=?, password_salt=?, "
				+ "password_expiration=?, firstname=?, lastname=?, uta_id=?, user_type=?, user_status=? "
				+ "WHERE id=?",
				config.DBNAME, User.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, this.user.getEmail());
			statement.setString(2, this.user.getUsername());
			statement.setString(3, this.user.getPasswordHash());
			statement.setString(4, this.user.getPasswordSalt());
			statement.setDate(5, new java.sql.Date(this.user.getPasswordExpiration().getTime()));
			statement.setString(6, this.user.getFirstName());
			statement.setString(7, this.user.getLastName());
			statement.setString(8, this.user.getUtaId());
			statement.setInt(9, this.user.getUserTypeId());
			statement.setInt(10, this.user.getStatus().code());
			statement.setInt(11, this.user.getId());
			statement.executeUpdate();

			String className = this.user.getClass().getSimpleName();
			String methodName = "update" + className + "Extra";
			Method method = this.getClass().getDeclaredMethod(methodName, this.user.getClass());
			method.invoke(this, this.user);
			result = true;
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception { }

	@SuppressWarnings("unused")
	private void updateStudentExtra(Student user) throws Exception {
		String query = String.format(
				"UPDATE %s.%s SET enrollment_status=?, student_type=? WHERE id=?",
				config.DBNAME, Student.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ((Student) this.user).getEnrollmentStatus().code());
			statement.setInt(2, ((Student) this.user).getStudentType().code());
			statement.setInt(3, this.user.getId());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	private void updateAdvisorExtra(Advisor user) throws Exception {
		String query = String.format(
				"UPDATE %s.%s SET email_notify=?, assigned_students=?, start_student=?, end_student=? WHERE id=?",
				config.DBNAME, Advisor.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setBoolean(1, ((Advisor) this.user).hasEmailNotification());
			statement.setBoolean(2, ((Advisor) this.user).isAssignedStudentRange());
			statement.setString(3, ((Advisor) this.user).getStartStudent());
			statement.setString(4, ((Advisor) this.user).getEndStudent());
			statement.setInt(5, this.user.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private void updateAdminExtra(Admin user) throws Exception { return; }
	
	@SuppressWarnings("unused")
	private void updateUserExtra(User user) throws Exception { return; }
}
