package edu.uta.cse.group9.database.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.uta.cse.group9.util.DBConfig;

// COMMAND PATTERN: 
public abstract class SQLCommand {

	protected final DBConfig config = DBConfig.getInstance();
	private final String dbUri = "jdbc:mysql://" + config.DBHOST + ":" + config.DBPORT + "/" + config.DBNAME;
	 
	protected Connection connection = null;
	protected PreparedStatement statement = null;
	protected ResultSet resultSet = null;
	protected Object result = null;
	
	// TEMPLATE PATTERN: Each command will use the template of
	// the execute method, providing only an implementation
	// of queryDB and processResult.
	final public void execute() throws Exception {
		connectDB();
		queryDB();
		processResult();
		disconnectDB();
	}
	
	private void connectDB() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(dbUri, config.DBUSER, config.DBPASSWORD);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	private void disconnectDB() throws Exception {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	abstract void queryDB() throws Exception;
	
	abstract void processResult() throws Exception;
	
	// Getters and Setters
	
	public Object getResult() {
		return result;
	}
}
