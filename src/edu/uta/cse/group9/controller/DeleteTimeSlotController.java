package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.ADVISOR_DELETE_TIME_SLOT)
public class DeleteTimeSlotController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Advisor.class.getName(),
		};
	
    public DeleteTimeSlotController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Implement
		// Serve confirm delete time slot page
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Implement
		// delete from database and serve response
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
