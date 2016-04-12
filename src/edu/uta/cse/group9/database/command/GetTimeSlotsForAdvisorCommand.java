package edu.uta.cse.group9.database.command;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.TimeSlot;

public class GetTimeSlotsForAdvisorCommand extends SQLCommand {

	// Variables
	
	private Advisor advisor;
	private Timestamp start;
	private Timestamp end;
	
	// Constructors
	
	public GetTimeSlotsForAdvisorCommand(Advisor advisor, Date start, Date end) {
		super();
		this.advisor = advisor;
		this.start = start != null ? new Timestamp(start.getTime()) : null;	
		this.end = end != null ? new Timestamp(end.getTime()) : null;
	}

	void setupQuery(Advisor advisor) throws Exception {
		String query = String.format("SELECT * FROM %s.%s WHERE advisor_id = ?", config.DBNAME, TimeSlot.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.advisor.getId());
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}		
	}

	void setupQuery(Advisor advisor, Timestamp start, Timestamp end) throws Exception {
		String query = String.format("SELECT * FROM %s.%s WHERE advisor_id = ? AND start_time >= ? AND end_time <= ?", 
				config.DBNAME, TimeSlot.TABLE_NAME);
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, advisor.getId());
			statement.setTimestamp(2, start);
			statement.setTimestamp(3, end);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	@Override
	void queryDB() throws Exception {
		if (this.advisor == null) {
			System.err.println("Advisor parameter not set.");
			return;
		}
		if (start == null && end == null) {
			setupQuery(this.advisor);
		} else if (start != null && end != null) {
			setupQuery(this.advisor, this.start, this.end);
		} else {
			// TODO could actually make it work for this case
			System.err.println("One date or other is null.");
			return;
		}
		try {
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {
		List<TimeSlot> timeslots = new ArrayList<TimeSlot>();
		try {
			while (resultSet.next()) {	
				Integer id = resultSet.getInt("id");
				Timestamp start = resultSet.getTimestamp("start_time");
				Timestamp end = resultSet.getTimestamp("end_time");
				TimeSlot slot = new TimeSlot(this.advisor, start, end);
				slot.setId(id);
				timeslots.add(slot);
			}
			result = timeslots;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
