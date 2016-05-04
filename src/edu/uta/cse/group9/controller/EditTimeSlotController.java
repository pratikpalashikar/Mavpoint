package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.ADVISOR_EDIT_TIME_SLOT)
public class EditTimeSlotController extends Controller {
	private static final long serialVersionUID = 1L;
	private Date PREV_START_TIME_SLOT;
	private Date PREV_END_TIME_SLOT;
	

	// Cancel appointment Email
	private static String USER_NAME = "testermavs@gmail.com"; // GMail user name
	// (just the
	// part
	// before "@gmail.com")
	private static String PASSWORD = "Simple12345"; // GMail password

	private static final String[] PERMISSIONS = new String[] { Advisor.class.getName(), };

	public EditTimeSlotController() {
		super();
	}

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Serve create time slot page
		Integer id = new Integer(request.getParameter("id"));
		User user = (User) request.getSession().getAttribute("user");
		DatabaseManager dbMgr = new DatabaseManager();
		try {
			TimeSlot timeslot = dbMgr.getTimeSlot(id);
			if (timeslot.getAdvisor().getId() != user.getId()) {
				request.setAttribute("error", "Unauthorized action.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			PREV_START_TIME_SLOT = timeslot.getStartTime();
			PREV_END_TIME_SLOT = timeslot.getEndTime();
			request.setAttribute("id", timeslot.getId());
			request.setAttribute("date", dateFormat.format(timeslot.getStartTime()));
			request.setAttribute("startTime", timeFormat.format(timeslot.getStartTime()));
			request.setAttribute("endTime", timeFormat.format(timeslot.getEndTime()));
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher(JSPMap.EDIT_TIMESLOT_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User advisor = (Advisor) request.getSession().getAttribute("user");
		String date = request.getParameter("date");
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		Integer timeslotId = new Integer(request.getParameter("id"));
		// Send an email to student using gmail
		String from = USER_NAME;
		String pass = PASSWORD;

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(String.format("%s %s", date, startTime));
			end = format.parse(String.format("%s %s", date, endTime));
		} catch (ParseException e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.EDIT_TIMESLOT_URL).forward(request, response);
			return;
		}
		// TODO: Cancel any appointments that occur if the timeslot length is
		// reduced
		DatabaseManager dbmgr = new DatabaseManager();
		try {
			TimeSlot timeslot = dbmgr.getTimeSlot(timeslotId);
			if (timeslot == null) {
				request.setAttribute("error", "Error retrieving Time Slot.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			} else if (timeslot.getAdvisor().getId() != advisor.getId()) {
				request.setAttribute("error", "Unauthorized action.");
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;
			} else {
				timeslot.setStartTime(start);
				timeslot.setEndTime(end);
				dbmgr.updateTimeSlot(timeslot);
				timeslot.setStartTime(PREV_START_TIME_SLOT);
				timeslot.setEndTime(PREV_END_TIME_SLOT);
				Object res = dbmgr.cancelShceduledAppointment(timeslot);

				List<String> strmail = (List<String>) res;
 				String to[] = new String[10];
 				int k=0;
				for(String strEmail:strmail){
					to[k++] = strEmail;
				}
				
				 // list of recipient email
				// addresses
				String subject = "Appointment Cancelled/Rescheduled by Advisor";
				String body = "Please book a new appointment slot as the current appointment is being cancelled by the advisor for the Day and time "
						+ PREV_START_TIME_SLOT;
				sendFromGMail(from, pass, to, subject, body);


			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
			return;
		}
		request.setAttribute("success", "Time Slot updated.");
		getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
		return;
	}

	private int getTimeSlotLength(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int startHour = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTime(end);
		int endHour = calendar.get(Calendar.HOUR_OF_DAY);
		return endHour - startHour;
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
				if(to[i]!=null)
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				if(toAddress[i]!=null)
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

	@Override
	protected String[] authorizedGetUsers() {
		return PERMISSIONS;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
