package edu.uta.cse.group9.util;

public final class LinkMap {
	private static final String APP_ROOT = "/MavAdvisor";
	
	// User URLs
	public static final String HOME = APP_ROOT + ServletMap.HOME;
	public static final String LOG_IN = APP_ROOT + ServletMap.LOG_IN;
	public static final String LOG_OUT = APP_ROOT + ServletMap.LOG_OUT;
	public static final String RESET_PASSWORD = APP_ROOT + ServletMap.RESET_PASSWORD;
	public static final String FEEDBACK = APP_ROOT + ServletMap.FEEDBACK;
	public static final String PREFERENCES = APP_ROOT + ServletMap.PREFERENCES;
	
	// Student URLs1
	public static final String STUDENT_CREATE_ACCOUNT = APP_ROOT + ServletMap.STUDENT_CREATE_ACCOUNT;
	public static final String APPOINTMENT_DETAIL = APP_ROOT + ServletMap.APPOINTMENT_DETAIL;
	public static final String APPOINTMENT_CANCEL = APP_ROOT + ServletMap.APPOINTMENT_CANCEL;
	public static final String STUDENT_CREATE_APPOINTMENT = APP_ROOT + ServletMap.STUDENT_CREATE_APPOINTMENT;	

	// Advisor URLs
	public static final String ADVISOR_LIST_TIME_SLOT = APP_ROOT + ServletMap.ADVISOR_LIST_TIME_SLOT;
	public static final String ADVISOR_CREATE_TIME_SLOT = APP_ROOT + ServletMap.ADVISOR_CREATE_TIME_SLOT;
	public static final String ADVISOR_EDIT_TIME_SLOT = APP_ROOT + ServletMap.ADVISOR_EDIT_TIME_SLOT;
	public static final String ADVISOR_DELETE_TIME_SLOT = APP_ROOT + ServletMap.ADVISOR_DELETE_TIME_SLOT;

	// Admin URLs
	public static final String ADMIN_CREATE_ADVISOR = APP_ROOT + ServletMap.ADMIN_CREATE_ADVISOR;
	public static final String ADMIN_DELETE_ADVISOR = APP_ROOT + ServletMap.ADMIN_DELETE_ADVISOR;
	
	// API URLs
	public static final String ASYNC_TIME_SLOTS = APP_ROOT + ServletMap.ASYNC_TIME_SLOTS;
	public static final String ASYNC_APPOINTMENTS = APP_ROOT + ServletMap.ASYNC_APPOINTMENTS;
}
