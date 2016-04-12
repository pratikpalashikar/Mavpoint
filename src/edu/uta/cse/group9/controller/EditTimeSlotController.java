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
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.ADVISOR_EDIT_TIME_SLOT)
public class EditTimeSlotController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Advisor.class.getName(),
		};

    public EditTimeSlotController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Serve create time slot page
		Integer id = new Integer(request.getParameter("id"));
		User user = (User) request.getSession().getAttribute("user");
		DatabaseManager dbMgr = new DatabaseManager();
		try {
			TimeSlot timeslot = dbMgr.getTimeSlot(id);
			if (timeslot.getAdvisor().getId() != user.getId()) {
				request.setAttribute("error", "Unauthorized action.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			request.setAttribute("id", timeslot.getId());
			request.setAttribute("date", dateFormat.format(timeslot.getStartTime()));
			request.setAttribute("startTime", timeFormat.format(timeslot.getStartTime()));
			request.setAttribute("endTime", timeFormat.format(timeslot.getEndTime()));
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher(JSPMap.EDIT_TIMESLOT_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User advisor = (Advisor) request.getSession().getAttribute("user");		
		String date = request.getParameter("date");
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		Integer timeslotId = new Integer(request.getParameter("id"));
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(String.format("%s %s", date, startTime));
			end = format.parse(String.format("%s %s", date, endTime));
		} catch (ParseException e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.EDIT_TIMESLOT_URL).forward(request, response);
			return;
		}
		// TODO: Cancel any appointments that occur if the timeslot length is reduced
		DatabaseManager dbmgr = new DatabaseManager();
		try {
			TimeSlot timeslot = dbmgr.getTimeSlot(timeslotId);
			if (timeslot == null) {
				request.setAttribute("error", "Error retrieving Time Slot.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			} else if (timeslot.getAdvisor().getId() != advisor.getId()) {
				request.setAttribute("error", "Unauthorized action.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			} else {
				timeslot.setStartTime(start);
				timeslot.setEndTime(end);
				dbmgr.updateTimeSlot(timeslot);
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
			return;
		}
		request.setAttribute("success", "Time Slot updated.");
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
	
	@Override
	protected String[] authorizedGetUsers() {
		return PERMISSIONS;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
