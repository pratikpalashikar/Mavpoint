package edu.uta.cse.group9.email;

public interface MailBuilderSupervisor {
	public void create(MailBuilder mailBuilder);
	public void construct();
	public Mail getProduct();
}
