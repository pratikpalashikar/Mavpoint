package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.ADVISOR_CREATE_TIME_SLOT)
public class CreateTimeSlotController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
		Advisor.class.getName()
	};

    public CreateTimeSlotController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Serve create time slot page
		String startTime = request.getParameter("starttime");
		if (startTime != null) {
			Date startDate = new Date(new Long(startTime));
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			request.setAttribute("startDate", dateFormat.format(startDate));
			request.setAttribute("startTime", timeFormat.format(startDate));
		}
		getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// extract parameters
		String startDate = request.getParameter("start_date");
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");

		// extract option parameters for recurring appointments
		String endDate = request.getParameter("end_date");
		String monStr = request.getParameter("mon");
		String tueStr = request.getParameter("tue");
		String wedStr = request.getParameter("wed");
		String thuStr = request.getParameter("thu");
		String friStr = request.getParameter("fri");
		Boolean mon = monStr != null && monStr.equals("on");
		Boolean tue = tueStr != null && tueStr.equals("on");
		Boolean wed = wedStr != null && wedStr.equals("on");
		Boolean thu = thuStr != null && thuStr.equals("on");
		Boolean fri = friStr != null && friStr.equals("on");

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(String.format("%s %s", startDate, startTime));
			if (endDate != null && !endDate.equals("")) {
				end = format.parse(String.format("%s %s", endDate, endTime));
			} else {
				end = format.parse(String.format("%s %s", startDate, endTime));
			}
		} catch (ParseException e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
			return;
		}

		Advisor advisor = (Advisor) request.getSession().getAttribute("user");
		DatabaseManager dbmgr = new DatabaseManager();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		
		// preserve start time and time slot length
		int slotLength = getTimeSlotLength(start, end); 
		calendar.setTime(start);
		int startHour = calendar.get(Calendar.HOUR_OF_DAY);
		int startMinute = calendar.get(Calendar.MINUTE);

		if (endDate.equals("")) {
			// Create single time slot and insert into the database
			TimeSlot timeslot = new TimeSlot(advisor, start, end);
			try {
				if(!dbmgr.insertTimeSlot(timeslot)){
					request.setAttribute("error", "An unknown error occurred.");
					getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
					return;
				}
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
				return;
			}
		} else {
			// Create multiple time slots
			while (calendar.getTime().getTime() <= end.getTime()) {
				// check if appointment should be scheduled for given day
				int day = calendar.get(Calendar.DAY_OF_WEEK);
				if (shouldScheduleForDay(day, mon, tue, wed, thu, fri)) {
					// reset the start time for that day and get the start date
					calendar.set(Calendar.HOUR_OF_DAY, startHour);
					calendar.set(Calendar.MINUTE, startMinute);
					Date slotStart = calendar.getTime();
					
					// increment by the slot length and get the end date
					calendar.add(Calendar.HOUR_OF_DAY, slotLength);
					Date slotEnd = calendar.getTime();
					
					// create the timeslot object and insert into the database
					TimeSlot timeslot = new TimeSlot(advisor, slotStart, slotEnd);
					try {
						if(!dbmgr.insertTimeSlot(timeslot)){
							request.setAttribute("error", "An unknown error occurred.");
							getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
							return;
						}
					} catch (Exception e) {
						request.setAttribute("error", e.getMessage());
						getServletContext().getRequestDispatcher(JSPMap.CREATE_TIMESLOT_URL).forward(request, response);
						return;
					} 				
				}
				// move calendar to the next day
				calendar.add(Calendar.HOUR, 24);
			}
		}		
		request.setAttribute("success", "Time Slots successfully created!");
		getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
		return;
	}
	
	private int getTimeSlotLength(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int startHour = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTime(end);
		int endHour = calendar.get(Calendar.HOUR_OF_DAY);
		return endHour - startHour;
	}
	
	private Boolean shouldScheduleForDay(int day, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri) {
		switch(day) {
		case(Calendar.MONDAY): return mon;
		case(Calendar.TUESDAY): return tue;
		case(Calendar.WEDNESDAY): return wed;
		case(Calendar.THURSDAY): return thu;
		case(Calendar.FRIDAY): return fri;
		default: return false;
		}
	}
	
	@Override
	protected String[] authorizedGetUsers() {
		return PERMISSIONS;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
