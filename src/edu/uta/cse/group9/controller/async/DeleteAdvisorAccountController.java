package edu.uta.cse.group9.controller.async;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.AppointmentStatus;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.model.UserStatus;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(urlPatterns = ServletMap.ADMIN_DELETE_ADVISOR, asyncSupported = true)
public class DeleteAdvisorAccountController extends AsyncController {

	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Admin.class.getName()
		};
	
    public DeleteAdvisorAccountController() {
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
    	Integer id = new Integer(request.getParameter("adv_id"));
    	String json = "";
    	Map<String,String> result = new HashMap<String,String>();
    	try {
        	Advisor advisor = (Advisor) dbMgr.getUserById(id);
        	User user = (User) request.getSession().getAttribute("user");
        	advisor.setStatus(UserStatus.INACTIVE);
        	advisor.setAssignedStudentRange(false);
        	String reason = "CANCELED: Advisor has been removed from the system.";
        	List<Appointment> appointments = dbMgr.getAppointmentsForUser(advisor, null, null); 
        	for (Appointment appt : appointments) {
        		StringBuilder sb = new StringBuilder(appt.getAdvisorNotes());
        		sb.append("\n" + reason);
        		appt.setAdvisorNotes(sb.toString());
        		appt.setStatus(AppointmentStatus.CANCELLED);
        		// TODO: Email both parties about the cancelation.
            	dbMgr.updateAppointment(appt);
        	}
        	dbMgr.updateUser(advisor);
        	result.put("success", "Advisor " + advisor.getId() + " removed.");
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
