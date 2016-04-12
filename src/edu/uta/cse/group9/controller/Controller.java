package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.ServletMap;

// Uses the PROTECTION PROXY and TEMPLATE METHOD patterns to enforce 
// user-based permissions access to different controller functions. 
public abstract class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("GET request: " + request.getRequestURL());
		ArrayList<String> permissions = new ArrayList<String>(Arrays.asList(authorizedGetUsers()));
		if (permissions == null || permissions.contains("None")) {
			request.setAttribute("error", "This method type is not allowed.");
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
			return;
		}
		try {
			if (validateUserPermission(request, permissions)) {
				performGet(request, response);
			} else {
				request.setAttribute("error", "You do not have permission to access the requested page.");
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			}			
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			if (!ServletMap.HOME.equals(request.getServletPath().toString())) {
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);	
			}
			return;
		}
	}

	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("POST request: " + request.getRequestURL());
		ArrayList<String> permissions = new ArrayList<String>(Arrays.asList(authorizedPostUsers()));
		if (permissions == null || permissions.contains("None")) {
			request.setAttribute("error", "This method type is not allowed.");
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
			return;
		}
		try {
			if (validateUserPermission(request, permissions)) {
				performPost(request, response);
			} else {
				request.setAttribute("error", "You do not have permission to access the requested page.");
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			}			
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			if (!ServletMap.HOME.equals(request.getServletPath().toString())) {
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);	
			}
			return;			
		}
	}

	protected void performGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { return; }
	
	protected void performPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { return; }
		
	protected String[] authorizedGetUsers() { return null; } 
	
	protected String[] authorizedPostUsers() { return null; }
	
	private final boolean validateUserPermission(HttpServletRequest request, ArrayList<String> permissions) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			// attempt to retrieve user from session token
			HttpSession session = request.getSession();
			String token = session.getId();
			user = new DatabaseManager().getUserByToken(token);
			session.setAttribute("user", user);
		}
		
		String userClass = "Anonymous";
		if (user != null) {
			userClass = user.getClass().getName();
		}
		if (permissions.contains("Any")) {
			return true;
		} else {
			return permissions.contains(userClass);	
		}
	}
}
