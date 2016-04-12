package edu.uta.cse.group9.database.command;

import java.sql.SQLException;

import edu.uta.cse.group9.model.Session;

public class TerminateSessionCommand extends SQLCommand {

	// Variables
	
	private String token = null;

	// Constructor
	
	public TerminateSessionCommand(String token) {
		super();
		this.token = token;
	}
	
	// Template Methods
	
	@Override
	void queryDB() throws Exception {
		
		if (this.token == null) {
			System.err.println("Token parameter not set.");
			return;
		}
		
		String query = String.format("DELETE FROM %s.%s where token = ?", 
				config.DBNAME, Session.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, this.token);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		this.result = (Boolean) true;
	}
}
