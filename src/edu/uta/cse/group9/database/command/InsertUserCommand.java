package edu.uta.cse.group9.database.command;

import java.lang.reflect.Method;
import java.sql.SQLException;

import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;

public class InsertUserCommand extends SQLCommand {
	
	private User user;
	
	public InsertUserCommand(User user){
		this.user = user;
	}
	
	@Override
	void queryDB() throws Exception {
		String query = String.format(
				"INSERT INTO %s.%s (email, username, password_hash, password_salt, "
				+ "password_expiration, firstname, lastname, uta_id, user_type, user_status,telephone) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
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
			statement.setString(11, this.user.getTelephone());
			statement.executeUpdate();

			// get the ID for the inserted user
			statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.toString());
				this.user.setId(resultSet.getInt("LAST_INSERT_ID()"));
			}
			String className = this.user.getClass().getSimpleName();
			String methodName = "insert" + className + "Extra";
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
	private void insertStudentExtra(Student user) throws Exception {
		String query = String.format(
				"INSERT INTO %s.%s (id, enrollment_status, student_type) VALUES (?,?,?)",
				config.DBNAME, Student.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.user.getId());
			statement.setInt(2, ((Student) this.user).getEnrollmentStatus().code());
			statement.setInt(3, ((Student) this.user).getStudentType().code());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	private void insertAdvisorExtra(Advisor user) throws Exception {
		String query = String.format(
				"INSERT INTO %s.%s (id, email_notify, assigned_students, start_student, end_student) VALUES (?,?,?,?,?)",
				config.DBNAME, Advisor.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.user.getId());
			statement.setBoolean(2, ((Advisor) this.user).hasEmailNotification());
			statement.setBoolean(3, ((Advisor) this.user).isAssignedStudentRange());
			statement.setString(4, ((Advisor) this.user).getStartStudent());
			statement.setString(5, ((Advisor) this.user).getEndStudent());
			statement.executeUpdate();
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private void insertAdminExtra(Admin user) throws Exception { return; }
	
	@SuppressWarnings("unused")
	private void insertUserExtra(User user) throws Exception { return; }
}
