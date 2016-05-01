package edu.uta.cse.group9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.STUDENT_RESET_PASSWORD)
public class ResetForgotPassword extends Controller {
	
	private static final long serialVersionUID = 1L;
	
	private static final String[] PERMISSIONS = new String[] {
			"Anonymous",
		};

	public ResetForgotPassword() {
		super();
	}
       
	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Serve student forgot password page
		getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_FORGOT_URL).forward(request, response);
	}
	
	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
