package edu.uta.cse.group9.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	// TODO: Implement methods to send email
	// Possibly use Builder pattern to construct email?

	// Variables
	
	private final String USERNAME = "mavadvisor@gmail.com";
	private final String PASSWORD = "M@vAdvis0r";
	private final String HOST = "smtp.gmail.com";
	private final String PORT = "587";
	
	/*
	 *  mavadvisormailer
		Access Key ID: AKIAJ5YUETJC52CU3LOA
		Secret Access Key: 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
	 */
	// Methods
	
	public void sendEmail(String to, String from, String subject, String message) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", PORT);
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		
		try {
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress(from));
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			email.setSubject(subject);
			email.setText(message);
			Transport.send(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
