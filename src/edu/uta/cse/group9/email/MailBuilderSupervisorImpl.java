package edu.uta.cse.group9.email;

public class MailBuilderSupervisorImpl implements MailBuilderSupervisor{
	private MailBuilder mailBuilder;

	@Override
	public void create(MailBuilder mailBuilder) {
		this.mailBuilder = mailBuilder;
	}

	@Override
	public void construct() {
		mailBuilder.construct();
	}

	@Override
	public Mail getProduct() {
		return mailBuilder.getProduct();
	}
	
}
