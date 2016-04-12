package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Session;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.LOG_IN)
public class LoginController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			"Anonymous",
		};
	
    public LoginController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Do nothing
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String token = session.getId();
		DatabaseManager dbmgr = new DatabaseManager();
		try {
			User user = dbmgr.getUserByUsername(username);
			if (user == null) {
				request.setAttribute("error", "Incorrect username and/or password");
			} else {
				if (user.verifyPassword(password)) {
					// TODO: Check password expiration and prompt to reset password if expired
					Session userSession = new Session(token, user);
					// save the new session in the table
					dbmgr.insertSession(userSession);
					session.setAttribute("user", user);
					request.setAttribute("success", "Successfully logged in!");
					
				} else {
					request.setAttribute("error", "Incorrect username and/or password");
				}
			}			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);		
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
