package edu.uta.cse.group9.database.command;

import java.lang.reflect.Method;
import java.sql.SQLException;

import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.EnrollmentStatus;
import edu.uta.cse.group9.model.Session;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.StudentType;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.model.UserStatus;

public class GetUserCommand extends SQLCommand {

	// Variables
	
	private Integer id;
	private String username;
	private String token;
	
	// Constructors
	
	public GetUserCommand(Integer id, String token, String username) {
		super();
		this.id = id;
		this.token = token;
		this.username = username;
	}
		
	@Override
	void queryDB() throws Exception {
		try {
			setupStatement(); 
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		try {
			User user = null;
			while (resultSet.next()) {
				String userClass = resultSet.getString("user_class");
				user = (User) Class.forName(userClass).newInstance();	
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setUsername(resultSet.getString("username"));
				user.setPasswordHash(resultSet.getString("password_hash"));
				user.setPasswordSalt(resultSet.getString("password_salt"));
				user.setPasswordExpiration(resultSet.getDate("password_expiration"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setUtaId(resultSet.getString("uta_id"));
				user.setStatus(UserStatus.forCode(new Integer(resultSet.getString("user_status"))));
				
				String className = user.getClass().getSimpleName();
				String methodName = "get" + className + "Extra";
				Method method = this.getClass().getDeclaredMethod(methodName, user.getClass());
				user = (User) method.invoke(this, user);
				result = user;
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new Exception(e.getMessage());
		}
	}

	private void setupStatement() throws Exception {
		String query = "";
		if (this.id != null) {
			query = String.format("SELECT * FROM %s.%s A, %s.user_type B WHERE A.id = ? and A.user_type = B.user_type_id",
					config.DBNAME, User.TABLE_NAME, config.DBNAME);
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
		} else if (this.token != null) {
			query = String.format("SELECT * FROM %s.%s A, %s.%s B, %s.user_type C WHERE B.token = ? AND A.id = B.user_id AND A.user_type = C.user_type_id",
					config.DBNAME, User.TABLE_NAME, config.DBNAME, Session.TABLE_NAME, config.DBNAME);
			statement = connection.prepareStatement(query);
			statement.setString(1, this.token);
		} else if (this.username != null) {
			if (this.username.contains("@")) {
				query = String.format("SELECT * FROM %s.%s A, %s.user_type B where A.email = ? and A.user_type = B.user_type_id", 
						config.DBNAME, User.TABLE_NAME, config.DBNAME);	
			} else {
				query = String.format("SELECT * FROM %s.%s A, %s.user_type B where A.username = ? and A.user_type = B.user_type_id",
						config.DBNAME, User.TABLE_NAME, config.DBNAME);	
			}
			statement = connection.prepareStatement(query);
			statement.setString(1, this.username);
		} else {
			throw new Exception("No parameters set!");
		}
	}
	
	@SuppressWarnings("unused")
	private User getStudentExtra(Student user) throws Exception {
		String query = String.format(
				"SELECT * FROM %s.%s where id = ?", config.DBNAME, Student.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user.setEnrollmentStatus(EnrollmentStatus.forCode(resultSet.getInt("enrollment_status")));
				user.setStudentType(StudentType.forCode(resultSet.getInt("student_type")));
			}
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
		return user;
	}
	
	@SuppressWarnings("unused")
	private User getAdvisorExtra(Advisor user) throws Exception {
		String query = String.format(
				"SELECT * FROM %s.%s where id = ?", config.DBNAME, Advisor.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user.setEmailNotification(resultSet.getBoolean("email_notify"));
				user.setAssignedStudentRange(resultSet.getBoolean("assigned_students"));
				user.setStartStudent(resultSet.getString("start_student"));
				user.setEndStudent(resultSet.getString("end_student"));
			}
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
		return user;
	}

	@SuppressWarnings("unused")
	private User getAdminExtra(Admin user) throws Exception { return user; }
	
	@SuppressWarnings("unused")
	private User getUserExtra(User user) throws Exception { return user; }
}
