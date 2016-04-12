package edu.uta.cse.group9.email;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class MailMaster {
	public final static String ACCESS_KEY = "AKIAJ5YUETJC52CU3LOA";
	public final static String SECRET_KEY = "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";
	
	public final static String FROMADDRESS = "nbsiva90@gmail.com";
	
	public static int sendEmail(Mail mail){
		Destination destination = (new Destination()).withToAddresses(new String[]{mail.getmTo()});
		Content subject = new Content().withData(mail.getmSubject());
		Content textBody = new Content().withData(mail.getmContent());
		
		Body body = new Body().withText(textBody);
		
		Message message = new Message().withSubject(subject).withBody(body);
		
		SendEmailRequest request = new SendEmailRequest().withSource(mail.getmFrom()).withDestination(destination).withMessage(message);
		
		try{
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
			client.setRegion(Region.getRegion(Regions.US_EAST_1));
			client.sendEmail(request);
			System.out.println("Email sent!");
		}catch(Exception ex){
			 System.out.println("The email was not sent.");
	         System.out.println("Error message: " + ex.getMessage());
		}
		return 0;
		
	}
	
}
