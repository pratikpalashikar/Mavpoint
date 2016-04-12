package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.util.JSPMap;

@WebServlet("/calendar")
public class CalendarController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			"Any"
		};
	
    public CalendarController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher(JSPMap.CALENDAR_TEST_URL).forward(request, response);
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