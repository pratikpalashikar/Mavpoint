package edu.uta.cse.group9.database.command;

import java.sql.SQLException;

import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;

public class GetAdvisingTaskCommand extends SQLCommand {

	// Variables
	
	private Integer id;
	private Advisor advisor;
	
	// Constructors
	
	public GetAdvisingTaskCommand(Integer id, Advisor advisor) {
		super();
		this.id = id;
		this.advisor = advisor;
	}
	
	@Override
	void queryDB() throws Exception {	
		if (this.id == null || this.advisor == null) {
			System.err.println("Id parameter and/or advisor not set.");
			return;
		}
		String query = String.format("SELECT * FROM %s.%s A WHERE A.id = ? AND A.advisor_id = ?",
				config.DBNAME, AdvisingTask.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
			statement.setInt(2, this.advisor.getId());
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			result = null;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		try {
			while (resultSet.next()) {
 
				String taskName = resultSet.getString("description");
				Integer id = resultSet.getInt("id");
				Integer duration = resultSet.getInt("duration");
				AdvisingTask task = new AdvisingTask(id, taskName, duration, this.advisor);
				result = task;
			}
		} catch (SQLException e) {
			result = null;
			throw new Exception(e.getMessage());
		}
	}
}
