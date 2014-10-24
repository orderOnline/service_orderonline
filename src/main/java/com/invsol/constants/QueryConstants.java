package com.invsol.constants;

public class QueryConstants {

	// Restaurant Specific Queries
	public static final String QUERY_AUTHENTICATE_RESTAURANT = "SELECT * FROM " + AppConstants.TABLE_RESTAURANT + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ? AND " + AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD + " = ?";
	public static final String QUERY_AUTHORIZE_BUSINESS_USER = "INSERT INTO " + AppConstants.TABLE_RESTAURANT + "("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + "," + AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD
			+ "," + AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + ")" + " VALUES (?,?,?)";
	public static final String QUERY_VALIDATE_RESTAURANT_OTPCODE = "SELECT "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID + " FROM " + AppConstants.TABLE_RESTAURANT + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + " = ?";
	public static final String QUERY_UPDATE_BUSINESS_USER_PROFILE = "UPDATE " + AppConstants.TABLE_RESTAURANT
			+ " SET (" + AppConstants.TABLE_RESTAURANT_COLUMN_NAME + "," + AppConstants.TABLE_RESTAURANT_COLUMN_EMAIL
			+ "," + AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CLOSEDON + "," + AppConstants.TABLE_RESTAURANT_COLUMN_ADDRESS + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_CITY + "," + AppConstants.TABLE_RESTAURANT_COLUMN_STATE + ","
			+ AppConstants.TABLE_RESTAURANT_COLUMN_ZIPCODE + ")" + " = (?,?,?,?,?,?,?,?,?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID + " = ?";
	public static final String QUERY_UPDATE_RESTAURANT_OTPCODE = "UPDATE " + AppConstants.TABLE_RESTAURANT
			+ " SET (" + AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + ")" + " = (?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ?";
	public static final String QUERY_UPDATE_RESTAURANT_PASSWORD = "UPDATE " + AppConstants.TABLE_RESTAURANT
			+ " SET (" + AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD + ")" + " = (?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ? AND " + AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + " = ?";

	// Cuisines Specific Queries
	public static final String QUERY_GET_COUNT_CUISINES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CUISINE;
	public static final String QUERY_GET_LIST_OF_ALL_CUISINES = "SELECT * FROM " + AppConstants.TABLE_CUISINE;

	// Restaurant Cuisines Specific Queries
	public static final String QUERY_INSERT_RESTAURANT_CUISINES_DETAILS = "INSERT INTO "
			+ AppConstants.TABLE_RESTAURANT_CUISINE + " VALUES (?,?)";

	// Category Specific Queries
	public static final String QUERY_INSERT_CATEGORY_DETAILS = "INSERT INTO " + AppConstants.TABLE_CATEGORY + "("
			+ AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_NAME + ","
			+ AppConstants.TABLE_CATEGORY_COLUMN_RESTAURANT_ID + ")" + " VALUES (?,?)";
	public static final String QUERY_GET_COUNT_CATEGORIES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CATEGORY
			+ " WHERE " + AppConstants.TABLE_CATEGORY_COLUMN_RESTAURANT_ID + " = ?";
	public static final String QUERY_SELECT_RESTAURANT_CATEGORIES = "SELECT "
			+ AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_ID + "," + AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_NAME
			+ " FROM " + AppConstants.TABLE_CATEGORY + " WHERE " + AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID
			+ " = ?";
	
	//MenuItem Specific Queries
	public static final String QUERY_INSERT_MENUITEM_DETAILS = "INSERT INTO " + AppConstants.TABLE_MENUITEMS + "("
			+ AppConstants.TABLE_MENUITEMS_COLUMN_NAME + ","
			+ AppConstants.TABLE_MENUITEMS_COLUMN_PRICE + ","
			+ AppConstants.TABLE_MENUITEMS_COLUMN_CUISINE_ID + ","
			+ AppConstants.TABLE_MENUITEMS_COLUMN_CATEGORY_ID + ")" + " VALUES (?,?,?,?)";

}
