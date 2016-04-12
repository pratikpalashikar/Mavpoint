package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.LOG_OUT)
public class LogoutController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			"Any"
		};

    public LogoutController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getSession().getId();
		request.getSession().invalidate();
		DatabaseManager dbmgr = new DatabaseManager();
		try {
			dbmgr.terminateSession(token);
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);		
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
