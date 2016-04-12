package edu.uta.cse.group9.controller.async;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.AppointmentStatus;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(urlPatterns = ServletMap.ASYNC_APPOINTMENTS, asyncSupported = true)
public class GetCalendarAppointmentsController extends AsyncController {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Advisor.class.getName(),
			Student.class.getName()
		};
	
	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// extract parameters
		Integer userId = Integer.parseInt(request.getParameter("user_id"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime;
		Date endTime;
		try {
			startTime = format.parse(start);
			endTime = format.parse(end);
		} catch (ParseException e) {
			System.out.println("Error parsing datetime.");
			startTime = null;
			endTime = null;
		}
		
		DatabaseManager dbmgr = new DatabaseManager();
		String json = "";
		try {
			User user = (User) dbmgr.getUserById(userId);
			String userClass = user.getClass().getSimpleName(); 
			if (userClass.equals("Advisor")) {
				json = retrieveAppointmentsForAdvisor(user, startTime, endTime);
			} else if (userClass.equals("Student")) {
				json = retrieveAppointmentsForStudent(user, startTime, endTime);
			} else {
				throw new Exception("Unauthorized action.");
			}
		} catch(Exception e) {
			 Map<String,String> map = new HashMap<String,String>();
			 map.put("error", e.getMessage());
			 json = gson.toJson(map);
		}
	    response.setContentType("text/html");
	    response.getWriter().write(json);
	}

	private String retrieveAppointmentsForAdvisor(User user, Date start, Date end) throws Exception {
		DatabaseManager dbmgr = new DatabaseManager();
		List<Appointment> appointments = dbmgr.getAppointmentsForUser(user, start, end);
		List<Object> result = new ArrayList<Object>();
		if (appointments != null) {
			for (Appointment appointment : appointments) {
				if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
					continue;
				}
				String title = String.format("%s\n%s, %s", 
						appointment.getTask().getName(),
						appointment.getStudent().getLastName(),
						appointment.getStudent().getFirstName());
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", String.valueOf(appointment.getId()));
				map.put("start", appointment.getStartTime().toString());
				map.put("end", appointment.getEndTime().toString());
				map.put("title", title);
				map.put("color", "green");
				result.add(map);
			}
		}
		return gson.toJson(result);
	}

	private String retrieveAppointmentsForStudent(User user, Date start, Date end) throws Exception {
		DatabaseManager dbmgr = new DatabaseManager();
		List<Advisor> advisors = dbmgr.getAdvisorList();
		
		// Find if student is assigned a specific advisor
		char first = user.getLastName().charAt(0);
		Advisor studentAdv = null;
		for (Advisor adv : advisors) {
			if (adv.isAssignedStudentRange()) {
				char advStart = adv.getStartStudent().charAt(0);
				char advEnd = adv.getEndStudent().charAt(0);
				if (first >= advStart && first <= advEnd) {
					studentAdv = adv;
					break;
				}
			}
		}
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		if (studentAdv != null) {
			// get only student's assigned advisor's time slots
			appointments = dbmgr.getAppointmentsForUser(studentAdv, start, end);
		} else {
			// get all time slots
			for (Advisor adv : advisors) {
				appointments.addAll(dbmgr.getAppointmentsForUser(adv, start, end));
			}			
		}
		List<Object> results = new ArrayList<Object>();
		if (appointments != null) {
			for (Appointment appt : appointments) {
				if (appt.getStatus() == AppointmentStatus.CANCELLED) {
					continue;
				}
				String title = "";
				String color = "grey";
				if (appt.getStudent().getId() != user.getId()) {
					title = "Student Appointment";					
					color = "grey";
				} else {
					title = String.format("%s\n%s %s", 
							appt.getTask().getName(),
							appt.getAdvisor().getFirstName(),
							appt.getAdvisor().getLastName());
					color = "green";
				}
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", String.valueOf(appt.getId()));
				map.put("start", appt.getStartTime().toString());
				map.put("end", appt.getEndTime().toString());
				map.put("title", title);
				map.put("color", color);
				results.add(map);
			}
		}
		return gson.toJson(results);
	}

	@Override
	protected String[] authorizedGetUsers() {
		return null;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
