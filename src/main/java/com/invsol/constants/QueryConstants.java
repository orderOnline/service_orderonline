package com.invsol.constants;

public class QueryConstants {

	// Restaurant Specific Queries
	public static final String QUERY_AUTHORIZE_BUSINESS_USER = "INSERT INTO " + AppConstants.TABLE_RESTAURANT + "("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + "," + AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD
			+ "," + AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + ")" + " VALUES (?,?,?)";
	public static final String QUERY_VALIDATE_RESTAURANT_OTPCODE = "SELECT "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID + " FROM " + AppConstants.TABLE_RESTAURANT + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + " = ?";
	/*public static final String QUERY_UPDATE_BUSINESS_USER_PROFILE = "INSERT INTO " + AppConstants.TABLE_RESTAURANT + "("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_NAME + "," + AppConstants.TABLE_RESTAURANT_COLUMN_EMAIL + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME + "," + AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CLOSEDON + "," + AppConstants.TABLE_RESTAURANT_COLUMN_ADDRESS + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CITY + "," + AppConstants.TABLE_RESTAURANT_COLUMN_STATE + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_ZIPCODE + ")" + " VALUES (?,?,?,?,?,?,?,?,?)";*/
	public static final String QUERY_UPDATE_BUSINESS_USER_PROFILE = "UPDATE " + AppConstants.TABLE_RESTAURANT + " SET ("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_NAME + "," + AppConstants.TABLE_RESTAURANT_COLUMN_EMAIL + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME + "," + AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CLOSEDON + "," + AppConstants.TABLE_RESTAURANT_COLUMN_ADDRESS + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CITY + "," + AppConstants.TABLE_RESTAURANT_COLUMN_STATE + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_ZIPCODE + ")" + " = (?,?,?,?,?,?,?,?,?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID + " = ?";

	// Cuisines Specific Queries
	public static final String QUERY_GET_COUNT_CUISINES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CUISINE;
	public static final String QUERY_GET_LIST_OF_ALL_CUISINES = "SELECT * FROM " + AppConstants.TABLE_CUISINE;

}
