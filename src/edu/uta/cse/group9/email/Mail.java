package edu.uta.cse.group9.email;

public class Mail {
	
	private String mTo;
	private String mFrom;
	private String mSubject;
	private String mContent;
	private String mContentType;
	
	public void setTo(String mTo) {
		this.mTo = mTo;
	}
	public void setFrom(String mFrom) {
		this.mFrom = mFrom;
	}
	public void setSubject(String mSubject) {
		this.mSubject = mSubject;
	}
	public void setContent(String mContent) {
		this.mContent = mContent;
	}
	public void setContentType(String mContentType) {
		this.mContentType = mContentType;
	}
		
	public String getmTo() {
		return mTo;
	}
	public String getmFrom() {
		return mFrom;
	}
	public String getmSubject() {
		return mSubject;
	}
	public String getmContent() {
		return mContent;
	}
	public String getmContentType() {
		return mContentType;
	}
}
