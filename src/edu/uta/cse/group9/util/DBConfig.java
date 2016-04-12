package edu.uta.cse.group9.util;

//SINGLETON PATTERN: Uses eager initialization to avoid
//possible issues with multi-threading.
//Convenience class for placing Database configuration parameters

public class DBConfig {

	private static DBConfig instance = new DBConfig();
	public final String DBNAME = "mavadvisor_dev";
	public final String DBHOST = "localhost";
	public final String DBPORT = "3306";
	public final String DBUSER = "root";
	public final String DBPASSWORD = "root";
	
	private DBConfig() {};
	
	public static DBConfig getInstance() {
		return instance;
	}	
}