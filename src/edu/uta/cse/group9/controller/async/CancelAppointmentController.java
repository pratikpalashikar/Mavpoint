package edu.uta.cse.group9.controller.async;

import java.io.IOException;
import java.util.HashMap;
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

@WebServlet(urlPatterns = ServletMap.APPOINTMENT_CANCEL, asyncSupported = true)
public class CancelAppointmentController extends AsyncController {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Student.class.getName(),
			Advisor.class.getName(),
		};	
       
    public CancelAppointmentController() {
        super();
    }

    @Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	// do nothing
	}

    @Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	DatabaseManager dbMgr = new DatabaseManager();
    	Integer id = new Integer(request.getParameter("appt_id"));
    	String reason = request.getParameter("reason");
    	String json = "";
    	Map<String,String> result = new HashMap<String,String>();
    	try {
        	Appointment appt = dbMgr.getAppointment(id);
        	User user = (User) request.getSession().getAttribute("user");
        	if (appt.getAdvisor().getId() == user.getId()) {
        		StringBuilder sb = new StringBuilder(appt.getAdvisorNotes());
        		sb.append("\nCANCELED BY ADVISOR: " + reason);
        		appt.setAdvisorNotes(sb.toString());
        	} else if (appt.getStudent().getId() == user.getId()) {
        		StringBuilder sb = new StringBuilder(appt.getStudentNotes());
        		sb.append("\nCANCELED BY STUDENT: " + reason);
        		appt.setStudentNotes(sb.toString());
        	} else {
        		throw new Exception("Unauthorized access.");
        	}
        	appt.setStatus(AppointmentStatus.CANCELLED);
        	// TODO: Email both parties about the cancelation.
        	dbMgr.updateAppointment(appt);
        	result.put("success", "Appointment " + id + " canceled.");
    	} catch (Exception e) {
    		result.put("error", e.getMessage());
		}
    	json = gson.toJson(result);
    	response.setContentType("application/json");
    	response.setCharacterEncoding("utf-8");
	    response.getWriter().write(json);
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
