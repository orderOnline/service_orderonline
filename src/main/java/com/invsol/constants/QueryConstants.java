package com.invsol.constants;

public class QueryConstants {

	// Restaurant Specific Queries
	public static final String QUERY_AUTHENTICATE_RESTAURANT = "SELECT * FROM " + AppConstants.TABLE_RESTAURANT
			+ " WHERE " + AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ? AND "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD + " = ?";
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
	public static final String QUERY_UPDATE_RESTAURANT_OTPCODE = "UPDATE " + AppConstants.TABLE_RESTAURANT + " SET ("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + ")" + " = (?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ?";
	public static final String QUERY_UPDATE_RESTAURANT_PASSWORD = "UPDATE " + AppConstants.TABLE_RESTAURANT + " SET ("
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PASSWORD + ")" + " = (?)" + " WHERE "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER + " = ? AND "
			+ AppConstants.TABLE_RESTAURANT_COLUMN_OTPCODE + " = ?";
	public static final String QUERY_GET_GCM_KEY_RESTAURANT = "SELECT " + AppConstants.TABLE_RESTAURANT_COLUMN_GCMKEY
			+ " FROM " + AppConstants.TABLE_RESTAURANT + " WHERE " + AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID
			+ " = ?";

	// Cuisines Specific Queries
	public static final String QUERY_GET_COUNT_CUISINES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CUISINE;
	public static final String QUERY_GET_LIST_OF_ALL_CUISINES = "SELECT * FROM " + AppConstants.TABLE_CUISINE;

	// Restaurant Cuisines Specific Queries
	public static final String QUERY_INSERT_RESTAURANT_CUISINES_DETAILS = "INSERT INTO "
			+ AppConstants.TABLE_RESTAURANT_CUISINE + " VALUES (?,?)";

	// Category Specific Queries
	public static final String QUERY_INSERT_CATEGORY_DETAILS = "INSERT INTO " + AppConstants.TABLE_CATEGORY + "("
			+ AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_NAME + "," + AppConstants.TABLE_CATEGORY_COLUMN_RESTAURANT_ID
			+ ")" + " VALUES (?,?)";
	public static final String QUERY_GET_COUNT_CATEGORIES = "SELECT COUNT(*) FROM " + AppConstants.TABLE_CATEGORY
			+ " WHERE " + AppConstants.TABLE_CATEGORY_COLUMN_RESTAURANT_ID + " = ?";
	public static final String QUERY_SELECT_RESTAURANT_CATEGORIES = "SELECT "
			+ AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_ID + "," + AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_NAME
			+ " FROM " + AppConstants.TABLE_CATEGORY + " WHERE " + AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID
			+ " = ?";

	// MenuItem Specific Queries
	public static final String QUERY_INSERT_MENUITEM_DETAILS = "INSERT INTO " + AppConstants.TABLE_MENUITEMS + "("
			+ AppConstants.TABLE_MENUITEMS_COLUMN_NAME + "," + AppConstants.TABLE_MENUITEMS_COLUMN_PRICE + ","
			+ AppConstants.TABLE_MENUITEMS_COLUMN_CUISINE_ID + "," + AppConstants.TABLE_MENUITEMS_COLUMN_CATEGORY_ID
			+ ")" + " VALUES (?,?,?,?)";

	// Order Specific Queries
	public static final String QUERY_INSERT_ORDER_DETAILS = "INSERT INTO " + AppConstants.TABLE_ORDERDETAILS + "("
			+ AppConstants.TABLE_ORDERDETAILS_RESTAURANTID + "," + AppConstants.TABLE_ORDERDETAILS_CONSUMERID + ","
			+ AppConstants.TABLE_ORDERDETAILS_TIMESTAMP + "," + AppConstants.TABLE_ORDERDETAILS_INSTRUCTION + ","
			+ AppConstants.TABLE_ORDERDETAILS_ORDERTOTAL + ")" + " VALUES (?,?,?,?,?)";
	public static final String QUERY_GET_ORDER_DETAILS = "SELECT " + AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_NAME + ","
			+ AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_ADDRESS + ","
			+ AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_CITY + ","
			+ AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_STATE + ","
			+ AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_PHONENUMBER + ","
			+ AppConstants.TABLE_CONSUMER + "." + AppConstants.TABLE_CONSUMER_COLUMN_ZIPCODE + ","
			+ AppConstants.TABLE_ORDERDETAILS + "." + AppConstants.TABLE_ORDERDETAILS_INSTRUCTION + ","
			+ AppConstants.TABLE_ORDERDETAILS + "." + AppConstants.TABLE_ORDERDETAILS_TIMESTAMP + ","
			+ " FROM " + AppConstants.TABLE_CONSUMER + "," + AppConstants.TABLE_ORDERDETAILS+ " WHERE " + AppConstants.TABLE_ORDERDETAILS_ORDERID
			+ " = ?";

	// Order MenuItems Specific Queries
	public static final String QUERY_INSERT_ORDERITEMS = "INSERT INTO " + AppConstants.TABLE_ORDERMENUITEMS + "("
			+ AppConstants.TABLE_ORDERMENUITEMS_ITEMID + "," + AppConstants.TABLE_ORDERMENUITEMS_QUANTITY + ","
			+ AppConstants.TABLE_ORDERMENUITEMS_ORDERID + ")" + " VALUES (?,?,?)";
	public static final String QUERY_GET_ORDERMENUITEMS_DETAILS = "SELECT "
			+ AppConstants.TABLE_MENUITEMS + "." + AppConstants.TABLE_MENUITEMS_COLUMN_NAME + ","
			+ AppConstants.TABLE_ORDERMENUITEMS + "." + AppConstants.TABLE_ORDERMENUITEMS_QUANTITY + ","
			+ " FROM " + AppConstants.TABLE_MENUITEMS + " INNER JOIN " + AppConstants.TABLE_ORDERMENUITEMS+ " ON " 
			+ AppConstants.TABLE_MENUITEMS + "." + AppConstants.TABLE_MENUITEMS_COLUMN_ITEM_ID + "="
			+ AppConstants.TABLE_ORDERMENUITEMS + "." + AppConstants.TABLE_ORDERMENUITEMS_ITEMID + " WHERE "
			+ AppConstants.TABLE_ORDERMENUITEMS + "." + AppConstants.TABLE_ORDERMENUITEMS_ORDERID + " = ?";
}
