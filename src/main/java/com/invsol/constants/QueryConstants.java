package com.invsol.constants;

public class QueryConstants {

	// Restaurant Specific Queries
	public static final String QUERY_AUTHORIZE_BUSINESS_USER = "INSERT INTO " + AppConstants.TABLE_RESTAURANT + "("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + "," + AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD
			+ "," + AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + ")" + " VALUES (?,?,?)";
	public static final String QUERY_VALIDATE_RESTAURANT_OTPCODE = "SELECT "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID + " FROM " + AppConstants.TABLE_RESTAURANT + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + " = ?";

	// Cuisines Specific Queries
	public static final String QUERY_GET_COUNT_CUISINES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CUISINE;
	public static final String QUERY_GET_LIST_OF_ALL_CUISINES = "SELECT * FROM " + AppConstants.TABLE_CUISINE;

}
