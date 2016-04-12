package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;

public class GetAdvisingTasksCommand extends SQLCommand {

	// Variables
	
	private Advisor advisor;
	
	// Constructors
	
	public GetAdvisingTasksCommand(Advisor advisor) {
		super();
		this.advisor = advisor;
	}
	
	@Override
	void queryDB() throws Exception {	
		if (this.advisor == null) {
			throw new Exception("Advisor parameter not specified!");
		}
		String query = String.format("SELECT * FROM %s.%s A WHERE A.advisor_id = ?",
				config.DBNAME, AdvisingTask.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.advisor.getId());
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			result = null;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		try {
			List<AdvisingTask> tasks = new ArrayList<AdvisingTask>();
			while (resultSet.next()) {
 				String taskName = resultSet.getString("description");
				Integer id = resultSet.getInt("id");
				Integer duration = resultSet.getInt("duration");
				AdvisingTask task = new AdvisingTask(id, taskName, duration, this.advisor);
				tasks.add(task);
			}
			result = tasks;
		} catch (SQLException e) {
			result = null;
			throw new Exception(e.getMessage());
		}
	}
}
