package edu.uta.cse.group9.email;

public class MailBuilderImpl implements MailBuilder{
	
	private Mail mMail;
	public MailBuilderImpl(){
		mMail = new Mail();
	}
	@Override
	public void addFrom(String from) {
		mMail.setFrom(from);
	}

	@Override
	public void addTo(String to) {
		mMail.setTo(to);
	}
	@Override
	public void addMimeType(String mimeType) {
		mMail.setContentType(mimeType);
	}

	@Override
	public void addContent(String content) {
		mMail.setContent(content);
	}

	@Override
	public void addSubject(String subject) {
		mMail.setSubject(subject);
	}
	@Override
	public void construct() {
	}
	
	@Override
	public Mail getProduct() {
		return mMail;
	}
}
