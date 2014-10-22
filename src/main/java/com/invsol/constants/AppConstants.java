package com.invsol.constants;

public class AppConstants {
	
	//DB SPECIFIC CONSTANTS
	public static String TABLE_RESTAURANT = "restaurant";
	public static String TABLE_RESTAURANT_COLUMN_PHONENUMBER = "phonenumber";
	public static String TABLE_RESTAURANT_COLUMN_PASSWORD = "password";
	public static String TABLE_CUISINE = "cusines";
	//-----------------------------------------------------------------------------------
	
	//JSON SPECIFIC CONSTANTS
	public static String JSON_RESULT = "result";
	public static String JSON_TYPE = "type";
	public static String JSON_RESPONSE = "response";
	public static String JSON_TYPE_SUCCESS = "success";
	public static String JSON_TYPE_ERROR = "error";
	public static String JSON_CUISINES = "cuisines";
	public static String JSON_CUISINE_ID = "cuisine_id";
	public static String JSON_CUISINE_NAME = "cuisine_name";
	public static String JSON_PASSWORD = "password";
	public static String JSON_PHONENUMBER = "phonenumber";
	//-----------------------------------------------------------------------------------
	
	public static final int GENERIC_APP_ERROR_CODE = 5001;	
	
	//ERROR CONSTANTS
	public static String ERROR_GENERIC = "Incorrect Request Data.";
	public static String ERROR_SQL_QUERY_EXECUTION = "Error executing SQL query.";
	public static String ERROR_USER_ALREADY_REGISTERED = "User already registered with the number.";
	//-----------------------------------------------------------------------------------
}
