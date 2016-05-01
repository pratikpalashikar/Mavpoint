package edu.uta.cse.group9.database.command;

import java.lang.reflect.Method;
import java.sql.SQLException;

import edu.uta.cse.group9.model.User;

public class UpdatePasswordCommand  extends SQLCommand {
	
	private User user;

	public UpdatePasswordCommand(User user) {
		super();
		this.user = user;
	}

	@Override
	void queryDB() throws Exception {
		String query = String.format(
				"UPDATE %s.%s SET password_hash=?, password_salt=? "
				+ "WHERE email=?",
				config.DBNAME, User.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, this.user.getPasswordHash());
			statement.setString(2, this.user.getPasswordSalt());
			statement.setString(3, this.user.getEmail());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	void processResult() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
