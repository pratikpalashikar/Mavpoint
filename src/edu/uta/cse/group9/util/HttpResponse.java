package edu.uta.cse.group9.util;

public class HttpResponse {
	int responseCode;
	String responseMessage;
	
	public static int RESPONSE_SUCESS = 200;
	public static int RESPONSE_INTERNAL_ERROR =500;
	public static int RESPONSE_UNAUTHORIZED = 401;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage.trim();
	}
}
