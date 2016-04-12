package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.sql.Timestamp;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.TimeSlot;

public class GetTimeSlotCommand extends SQLCommand {

	// Variables
	
	private Integer id;
	
	// Constructors
	
	public GetTimeSlotCommand(Integer id) {
		super();
		this.id = id;
	}
	
	@Override
	void queryDB() throws Exception {
		if (this.id == null) {
			System.err.println("Id parameter not set.");
			return;
		}
		try {
			String query = String.format("SELECT * FROM %s.%s WHERE id = ?", config.DBNAME, TimeSlot.TABLE_NAME);
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		try {
			DatabaseManager dbMgr = new DatabaseManager();
			while (resultSet.next()) {	
				Integer id = resultSet.getInt("id");
				Timestamp start = resultSet.getTimestamp("start_time");
				Timestamp end = resultSet.getTimestamp("end_time");
				Advisor adv = (Advisor) dbMgr.getUserById(resultSet.getInt("advisor_id"));
				TimeSlot slot = new TimeSlot(adv, start, end);
				slot.setId(id);
				result = slot;
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
