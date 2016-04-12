package edu.uta.cse.group9.email;

public class MailClient {
	public void test(){
		MailBuilder mailBuilder = new MailBuilderImpl();
		mailBuilder.addContent("Content");
		mailBuilder.addFrom(MailMaster.FROMADDRESS);
		mailBuilder.addMimeType("mimeType");
		mailBuilder.addSubject("subject");
		mailBuilder.addTo("nbsiva90@gmail.com");
		
		MailBuilderSupervisor mailSupervisor = new MailBuilderSupervisorImpl();
		mailSupervisor.create(mailBuilder);
		Mail mail = mailSupervisor.getProduct();
		MailMaster.sendEmail(mail);
	}
}
