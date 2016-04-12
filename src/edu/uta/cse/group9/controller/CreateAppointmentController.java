package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.text.DateFormat;
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
import edu.uta.cse.group9.model.AdvisingTask;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.AppointmentStatus;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.STUDENT_CREATE_APPOINTMENT)
public class CreateAppointmentController extends Controller {
	private static final long serialVersionUID = 1L;

	private static String USER_NAME = "testermavs@gmail.com"; // GMail user name (just the part
											// before "@gmail.com")
	private static String PASSWORD = "Simple12345"; // GMail password
	private static String RECIPIENT = "lizard.bill@myschool.edu";

	private static final String[] PERMISSIONS = new String[] { "Anonymous", Student.class.getName() };

	public CreateAppointmentController() {
		super();
	}

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long startMillis = new Long(request.getParameter("starttime"));
		Integer timeSlotId = new Integer(request.getParameter("timeslot_id"));
		if (startMillis != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(startMillis);
			Date startDate = calendar.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
			request.setAttribute("date", dateFormat.format(startDate));
			request.setAttribute("start_time", timeFormat.format(startDate));
		}
		DatabaseManager dbMgr = new DatabaseManager();
		try {
			TimeSlot ts = dbMgr.getTimeSlot(timeSlotId);
			if (ts == null) {
				throw new Exception("Time slot not found.");
			}
			Advisor advisor = ts.getAdvisor();
			List<AdvisingTask> tasks = dbMgr.getAdvisingTasks(advisor);
			request.setAttribute("advisor", advisor);
			request.setAttribute("tasks", tasks);
			getServletContext().getRequestDispatcher(JSPMap.CREATE_APPOINTMENT_URL).forward(request, response);
			return;
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.CREATE_APPOINTMENT_URL).forward(request, response);
			return;
		}
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer advisorId = Integer.parseInt(request.getParameter("advisor_id"));
		Integer advisingTask = Integer.parseInt(request.getParameter("advising_task"));
		String date = request.getParameter("date");
		String start = request.getParameter("start_time");
		String end = request.getParameter("end_time");
		String studentNotes = request.getParameter("student_notes");

		DateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		Date startTime;
		Date endTime;
		try {
			startTime = format.parse(date + " " + start);
			endTime = format.parse(date + " " + end);
		} catch (ParseException e) {
			request.setAttribute("error", "Unable to parse date/time format!");
			getServletContext().getRequestDispatcher(JSPMap.CREATE_APPOINTMENT_URL).forward(request, response);
			return;
		}

		try {
			DatabaseManager dbmgr = new DatabaseManager();
			Student student = (Student) request.getSession().getAttribute("user");
			Advisor advisor = (Advisor) dbmgr.getUserById(advisorId);
			AdvisingTask task = dbmgr.getAdvisingTask(advisingTask, advisor);

			Appointment appointment = new Appointment(null, advisor, student, startTime, endTime, studentNotes, "",
					task, AppointmentStatus.SCHEDULED);
			if (dbmgr.insertAppointment(appointment)) {
				request.setAttribute("success", "Appointment successfully scheduled!");

				// Send email to student
				// MailBuilder mailBuilder = new MailBuilderImpl();
				// MailBuilderSupervisor mailSupervisor = new
				// MailBuilderSupervisorImpl();
				// mailBuilder.addContent("Content");
				// mailBuilder.addFrom(MailMaster.FROMADDRESS);
				// mailBuilder.addMimeType("mimeType");
				// mailBuilder.addSubject("Appointment Scheduled");
				// mailBuilder.addContent("You have an appointment scheduled for
				// " + appointment.getStartTime().toString());
				// mailBuilder.addTo(student.getEmail());
				// mailSupervisor.create(mailBuilder);
				// Mail mailToStudent = mailSupervisor.getProduct();
				// MailMaster.sendEmail(mailToStudent);

				// Send an email to student using gmail
				String from = USER_NAME;
				String pass = PASSWORD;
				String[] to = { student.getEmail() }; // list of recipient email
														// addresses
				String subject = "Appointment Scheduled";
				String body = "You have an appointment scheduled for " + appointment.getStartTime().toString();

				sendFromGMail(from, pass, to, subject, body);

				// send email to advisor only if advisor is registered for
				// notifications.
				if (advisor.hasEmailNotification()) {

					from = USER_NAME;
					pass = PASSWORD;
					String[] toAdv = { advisor.getEmail() }; // list of recipient
															// email addresses
					subject = "Appointment Scheduled";
					body = "You have an appointment scheduled for " + appointment.getStartTime().toString();

					sendFromGMail(from, pass, toAdv, subject, body);

				}
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			} else {
				request.setAttribute("error", "An unknown error occurred.");
				getServletContext().getRequestDispatcher(JSPMap.CREATE_APPOINTMENT_URL).forward(request, response);
				return;
			}
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher(JSPMap.CREATE_APPOINTMENT_URL).forward(request, response);
			return;
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
	            for( int i = 0; i < to.length; i++ ) {
	                toAddress[i] = new InternetAddress(to[i]);
	            }

	            for( int i = 0; i < toAddress.length; i++) {
	                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	            }

	            message.setSubject(subject);
	            message.setText(body);
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, from, pass);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        }
	        catch (AddressException ae) {
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
