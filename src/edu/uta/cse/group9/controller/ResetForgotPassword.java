package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.UserStatus;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.SecurityUtil;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.STUDENT_RESET_PASSWORD)
public class ResetForgotPassword extends Controller {

	private static final long serialVersionUID = 1L;

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	private static String USER_NAME = "testermavs@gmail.com"; // GMail user name
																// (just the
																// part
	// before "@gmail.com")
	private static String PASSWORD = "Simple12345"; // GMail password
	private static String RECIPIENT = "lizard.bill@myschool.edu";

	private static final String[] PERMISSIONS = new String[] { "Anonymous", };

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
		String resetEmail = request.getParameter("resetEmail");
		String OTP = randomString(6);
		// Send an email to student using gmail
		String from = USER_NAME;
		String pass = PASSWORD;
		// Generate the salt and the hash
		// generate password salt and hash
		SecurityUtil security = new SecurityUtil();
		String salt = security.generateSalt();
		String hashPassword = null;

		String[] to = { resetEmail }; // list of recipient email
										// addresses
		String subject = "Mav Advisor Reset Password";
		String body = "Your one time password is : " + " " + OTP;
		// update the password in the database

		try {
			hashPassword = security.getHash(OTP, salt);
		} catch (NoSuchAlgorithmException e) {
			request.setAttribute("error", "Hash algorithm not found.");
			getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
			return;
		}

		// Its just an object but it will be common for all the user as we can
		// set the password using the email of the user
		Student student = new Student(null, resetEmail, null, hashPassword, salt, null, null, null, null, null, null,
				null, UserStatus.ACTIVE);

		// Create a database manager object
		DatabaseManager dbmgr = new DatabaseManager();

		try {
			if (dbmgr.updatePassword(student)) {

				sendFromGMail(from, pass, to, subject, body);
				// After sending the one time password redirect it to URL

				request.setAttribute("success", "One time password sent on your registered email");
				getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
			} else {
				request.setAttribute("error", "An unknown error occurred.Failed to send email verification");
				getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.USER_RESET_PASSWORD_URL).forward(request, response);
		}

	}

	private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
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
