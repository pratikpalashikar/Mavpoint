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
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.UserStatus;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(urlPatterns = ServletMap.ASYNC_TIME_SLOTS, asyncSupported = true)
public class GetTimeSlotsController extends AsyncController {
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
		String advisorId = request.getParameter("advisor_id");
		String studentId = request.getParameter("student_id");
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

	    String json = "";
	    try {
		    if (advisorId != null) {
				json = retrieveTimeSlotsForAdvisor(Integer.parseInt(advisorId), startTime, endTime);
			} else if (studentId != null) {
				json = retrieveTimeSlotsForStudent(Integer.parseInt(studentId), startTime, endTime);
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
	
	private String retrieveTimeSlotsForAdvisor(Integer id, Date start, Date end) throws Exception {
		DatabaseManager dbmgr = new DatabaseManager();
	    List<Object> result = new ArrayList<Object>();
		Advisor advisor = (Advisor) dbmgr.getUserById(id);
		List<TimeSlot> timeslots = dbmgr.getTimeSlotsForAdvisor(advisor, start, end);
		if (timeslots != null) {
			for (TimeSlot timeslot : timeslots) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", String.valueOf(timeslot.getId()));
				map.put("start", timeslot.getStartTime().toString());
				map.put("end", timeslot.getEndTime().toString());
				map.put("color", "blue");
				result.add(map);
			}
		}
	    return gson.toJson(result);
	}

	private String retrieveTimeSlotsForStudent(Integer id, Date start, Date end) throws Exception {
		DatabaseManager dbmgr = new DatabaseManager();
	    List<Object> result = new ArrayList<Object>();
		Student student = (Student) dbmgr.getUserById(id);
		List<Advisor> advisors = dbmgr.getAdvisorList();
		
		// Find if student is assigned a specific advisor
		char first = student.getLastName().charAt(0);
		Advisor studentAdv = null;
		for (Advisor adv : advisors) {
			if (adv.isAssignedStudentRange() && adv.getStatus() == UserStatus.ACTIVE) {
				char advStart = adv.getStartStudent().charAt(0);
				char advEnd = adv.getEndStudent().charAt(0);
				if (first >= advStart && first <= advEnd) {
					studentAdv = adv;
					break;
				}
			}
		}
		List<TimeSlot> timeslots = new ArrayList<TimeSlot>();
		if (studentAdv != null) {
			// get only student's assigned advisor's time slots
			timeslots = dbmgr.getTimeSlotsForAdvisor(studentAdv, start, end);
		} else {
			// get all time slots
			for (Advisor adv : advisors) {
				if (adv.getStatus() == UserStatus.ACTIVE) {
					timeslots.addAll(dbmgr.getTimeSlotsForAdvisor(adv, start, end));	
				}
			}			
		}
		if (timeslots != null) {
			for (TimeSlot timeslot : timeslots) {
				String advName = timeslot.getAdvisor().getFirstName() + " " + timeslot.getAdvisor().getLastName();
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", String.valueOf(timeslot.getId()));
				map.put("title", advName);
				map.put("start", timeslot.getStartTime().toString());
				map.put("end", timeslot.getEndTime().toString());
				map.put("color", "blue");
				result.add(map);
			}
		}
	    return gson.toJson(result);		
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
