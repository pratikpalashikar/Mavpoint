package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.UserStatus;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.SecurityUtil;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.ADMIN_CREATE_ADVISOR)
public class CreateAdvisorAccountController extends Controller {

	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Admin.class.getName()
		};
	
    public CreateAdvisorAccountController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String email;
		// extract parameters
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		//String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		String utaId = request.getParameter("uta_id");
		//No enrollment concept
		email = request.getParameter("email");
		
		String telephone = request.getParameter("tel");
		
		if (!password.equals(confirmPassword)){
			request.setAttribute("error", "Passwords do not match!");
			getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
			return;
		}
		
		SecurityUtil security = new SecurityUtil();
		String salt = security.generateSalt();
		String hashPassword = null;
		try {
			hashPassword = security.getHash(password, salt);
		} catch (NoSuchAlgorithmException e) {
			request.setAttribute("error", "Hash algorithm not found.");
			getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 6);
		Date expiration = calendar.getTime();
		// TODO: Remove password fields and implement generated password
		String randPassword = security.generateRandomPassword(); 
		Advisor advisor = new Advisor(null, email, username, hashPassword, salt, expiration, firstname, lastname,
				utaId, true, false, null, null,telephone, UserStatus.ACTIVE);
		
		DatabaseManager dbmgr = new DatabaseManager();
		try {
			if(dbmgr.insertUser(advisor)){
				request.setAttribute("success", String.format("%s %s successfully registered!", advisor.getFirstName(), advisor.getLastName()));
				// TODO: Send email to Advisor with password
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			} else {
				request.setAttribute("error", "An unknown error occurred.");
				getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
				return;
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
			return;
		}
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
