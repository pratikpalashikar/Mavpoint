package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.HOME)
public class HomeController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			"Any"
		};
	
    public HomeController() {
        super();
    }

    @Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = (User) request.getSession().getAttribute("user");
    	String error = request.getParameter("error");
    	if (error != null) {
    		request.setAttribute("error", error);
    	}
    	String success = request.getParameter("success");
    	if (success != null) {
    		request.setAttribute("success", success);
    	}
    	if (user != null) {
    		try {
           		DatabaseManager dbmgr = new DatabaseManager();
           		List<Appointment> appointments = dbmgr.getAppointmentsForUser(user, null, null);
           		if (user.getClass().getName().equals(Admin.class.getName())) {
           			List<Advisor> advisors = dbmgr.getAdvisorList();
           			request.setAttribute("advisors", advisors);
           		}
           		request.setAttribute("appointments", appointments);     		    			
    		} catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;	
    		}
    	}
		getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performGet(request, response);
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