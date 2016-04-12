package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.User;

public class GetAdvisorListCommand extends SQLCommand {

	// Variables
	
	// Constructors
	
	public GetAdvisorListCommand() {
		super();
	}

	@Override
	void queryDB() throws Exception {
		// TODO Update to user enum
		String query = String.format("SELECT * FROM %s.%s A, %s.user_type B WHERE A.user_type = 2 AND A.user_type = B.user_type_id",
				config.DBNAME, User.TABLE_NAME, config.DBNAME);
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		List<User> users = new ArrayList<User>();
		try {
			while (resultSet.next()) {	
				Integer id = resultSet.getInt("id");
				SQLCommand cmd = new GetUserCommand(id, null, null);
				cmd.execute();
				Advisor adv = (Advisor) cmd.getResult();
				users.add(adv);
			}
			result = users;
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new Exception(e.getMessage());
		}
	}
}
