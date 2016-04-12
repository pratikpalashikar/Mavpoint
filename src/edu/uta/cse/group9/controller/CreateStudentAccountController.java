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
import edu.uta.cse.group9.model.EnrollmentStatus;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.StudentType;
import edu.uta.cse.group9.model.UserStatus;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.SecurityUtil;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.STUDENT_CREATE_ACCOUNT)
public class CreateStudentAccountController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			"Anonymous",
		};
       
    public CreateStudentAccountController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Serve student registration page
		getServletContext().getRequestDispatcher(JSPMap.CREATE_STUDENT_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email,utaId="";
		// Extract parameters from form
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");

		if(Integer.valueOf(request.getParameter("enrollment_type"))==0){
			email = request.getParameter("personalEmail");
			
		}else{
			email = request.getParameter("email");
			utaId = request.getParameter("uta_id");
		}
		String telephone = request.getParameter("tel");
		EnrollmentStatus enrollment = EnrollmentStatus.forCode(Integer.valueOf(request.getParameter("enrollment_type")));
		StudentType studentType = StudentType.forCode(Integer.valueOf(request.getParameter("student_type")));

		// TODO - Validate parameters
		if (!password.equals(confirmPassword)) {
			request.setAttribute("error", "Passwords do not match!");
			getServletContext().getRequestDispatcher(JSPMap.CREATE_STUDENT_URL).forward(request, response);
			return;
		}
		
		// generate password salt and hash
		SecurityUtil security = new SecurityUtil();
		String salt = security.generateSalt();
		String hashPassword = null;
		try {
			hashPassword = security.getHash(password, salt);
		} catch (NoSuchAlgorithmException e) {
			request.setAttribute("error", "Hash algorithm not found.");
			getServletContext().getRequestDispatcher(JSPMap.CREATE_STUDENT_URL).forward(request, response);
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 6);
		Date expiration = calendar.getTime();
		Student student = new Student(null, email, username, hashPassword, salt, expiration, firstname, lastname,
				utaId, enrollment, studentType,telephone, UserStatus.ACTIVE);

		DatabaseManager dbmgr = new DatabaseManager();
		try {
			if(dbmgr.insertUser(student)){
				request.setAttribute("success", String.format("%s %s successfully registered!", student.getFirstName(), student.getLastName()));
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			} else {
				request.setAttribute("error", "An unknown error occurred.");
				getServletContext().getRequestDispatcher(JSPMap.CREATE_STUDENT_URL).forward(request, response);
				return;
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.CREATE_STUDENT_URL).forward(request, response);
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
