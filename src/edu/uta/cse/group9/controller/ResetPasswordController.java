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
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.SecurityUtil;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.RESET_PASSWORD)
public class ResetPasswordController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Student.class.getName(),
			Advisor.class.getName(),
			Admin.class.getName()
		};
       
    public ResetPasswordController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password = request.getParameter("current_password");
		String new_password = request.getParameter("new_password");
		String new_c_password = request.getParameter("c_new_password");
		
		DatabaseManager dbmgr = new DatabaseManager();
		
		if(!new_password.equals(new_c_password)){
			request.setAttribute("error", "Password do not match!");
			getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
			return;
		}
		
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				request.setAttribute("error", "User not found!");
				getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
				return;
			} else {
				if (user.verifyPassword(password)) {
					SecurityUtil security = new SecurityUtil();
					String salt = security.generateSalt();
					String hashPassword = null;
					try {
						hashPassword = security.getHash(new_password, salt);
					} catch (NoSuchAlgorithmException e) {
						request.setAttribute("error", "Hash algorithm not found.");
						getServletContext().getRequestDispatcher(JSPMap.CREATE_ADVISOR_URL).forward(request, response);
						return;
					}
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, 6);
					Date expiration = calendar.getTime();
					user.setPasswordExpiration(expiration);
					user.setPasswordHash(hashPassword);
					user.setPasswordSalt(salt);
					
					if(dbmgr.updateUser(user)){
						request.setAttribute("success", String.format("%s Password updated successfully!", user.getFirstName()));
						getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
						return;
					} else {
						request.setAttribute("error", "Unknown error. Please try after sometime");
						getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "Current password entered incorrectly.");
					getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
					return;					
				}
			}			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
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
