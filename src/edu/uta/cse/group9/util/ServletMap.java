package edu.uta.cse.group9.util;

public final class ServletMap {
	
	// These URLs are used for the @WebServlet annotations
	
	// User URLs
	public static final String HOME = "/";
	public static final String LOG_IN = "/login";
	public static final String LOG_OUT = "/logout";
	public static final String RESET_PASSWORD = "/password";
	public static final String FEEDBACK = "/feedback";
	public static final String PREFERENCES = "/preferences";
	
	// Student URLs
	public static final String STUDENT_CREATE_ACCOUNT = "/student/resetPassword";
	public static final String STUDENT_RESET_PASSWORD = "/student/create";
	public static final String APPOINTMENT_DETAIL = "/appointment/show";
	public static final String APPOINTMENT_CANCEL = "/appointment/cancel";
	public static final String STUDENT_CREATE_APPOINTMENT = "/appointment/create";	

	// Advisor URLs
	public static final String ADVISOR_LIST_TIME_SLOT = "/advisor/timeslot";
	public static final String ADVISOR_CREATE_TIME_SLOT = "/advisor/timeslot/create";
	public static final String ADVISOR_EDIT_TIME_SLOT = "/advisor/timeslot/edit";
	public static final String ADVISOR_DELETE_TIME_SLOT = "/advisor/timeslot/delete";

	// Admin URLs
	public static final String ADMIN_CREATE_ADVISOR = "/advisor/create";
	public static final String ADMIN_DELETE_ADVISOR = "/advisor/delete";
	
	// Async URLs
	public static final String ASYNC_TIME_SLOTS = "/api/timeslot";
	public static final String ASYNC_APPOINTMENTS = "/api/appointment";
}
