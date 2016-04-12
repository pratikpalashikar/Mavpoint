package edu.uta.cse.group9.database.command;

import java.sql.SQLException;

import edu.uta.cse.group9.model.Session;

public class InsertSessionCommand extends SQLCommand {

	// Variables
	
	private Session session = null;
	
	// Constructors
	
	public InsertSessionCommand(Session session) {
		this.session = session;
	}
	
	@Override
	void queryDB() throws Exception {
		if (this.session == null) {
			System.err.println("Session not set.");
			return;
		}
		String query = null;
		query = String.format("INSERT INTO %s.%s (token, user_id) VALUES (?, ?) " +
					"ON DUPLICATE KEY UPDATE token = ?",
					config.DBNAME, Session.TABLE_NAME);	
		
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, this.session.getToken());
			statement.setInt(2, this.session.getUser().getId());
			statement.setString(3, this.session.getToken());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() {
		result = (Boolean) true;
	}
}
