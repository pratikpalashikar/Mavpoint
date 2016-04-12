package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.APPOINTMENT_DETAIL)
public class ShowAppointmentController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Student.class.getName(),
			Advisor.class.getName(),
		};

    public ShowAppointmentController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer apptId = new Integer(request.getParameter("appt_id"));
		DatabaseManager dbMgr = new DatabaseManager();
		Appointment appt = null;
		try {
			appt = dbMgr.getAppointment(apptId);
			User user = (User) request.getSession().getAttribute("user");
			// must verify that only either the student or the advisor are viewing the appointment
			if (appt.getAdvisor().getId() != user.getId() && appt.getStudent().getId() != user.getId()) {
				throw new Exception("Unauthorized access.");
			} else {
				request.setAttribute("appt", appt);	
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher(JSPMap.SHOW_APPOINTMENT_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
