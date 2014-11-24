package com.invsol.constants;

public class AppConstants {
	
	//DB SPECIFIC CONSTANTS
	public static String TABLE_RESTAURANT = "restaurant";
	public static String TABLE_RESTAURANT_COLUMN_RESTAURANT_ID = "restaurant_id";
	public static String TABLE_RESTAURANT_COLUMN_PHONENUMBER = "phonenumber";
	public static String TABLE_RESTAURANT_COLUMN_PASSWORD = "password";
	public static String TABLE_RESTAURANT_COLUMN_OTPCODE = "otp_code";
	public static String TABLE_RESTAURANT_COLUMN_NAME = "name";
	public static String TABLE_RESTAURANT_COLUMN_EMAIL = "email";
	public static String TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME = "service_start_time";
	public static String TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME = "service_end_time";
	public static String TABLE_RESTAURANT_COLUMN_CLOSEDON = "closedon";
	public static String TABLE_RESTAURANT_COLUMN_ADDRESS = "address";
	public static String TABLE_RESTAURANT_COLUMN_CITY = "city";
	public static String TABLE_RESTAURANT_COLUMN_STATE = "state";
	public static String TABLE_RESTAURANT_COLUMN_ZIPCODE = "zipcode";
	public static String TABLE_RESTAURANT_COLUMN_GCMKEY = "gcm_reg_key";
	
	public static String TABLE_CUISINE = "cusines";
	
	public static String TABLE_RESTAURANT_CUISINE = "restaurant_cusine";
	public static String TABLE_RESTAURANT_CUISINES_COLUMN_RESTAURANT_ID = "restaurant_id";
	public static String TABLE_RESTAURANT_CUISINES_COLUMN_CUISINE_ID = "cusine_id";
	
	public static String TABLE_CATEGORY = "categories";
	public static String TABLE_CATEGORY_COLUMN_CATEGORY_NAME = "category_name";
	public static String TABLE_CATEGORY_COLUMN_CATEGORY_ID = "category_id";
	public static String TABLE_CATEGORY_COLUMN_RESTAURANT_ID = "restaurant_id";
	
	public static String TABLE_MENUITEMS = "menuitems";
	public static String TABLE_MENUITEMS_COLUMN_NAME = "name";
	public static String TABLE_MENUITEMS_COLUMN_PRICE = "price";
	public static String TABLE_MENUITEMS_COLUMN_CUISINE_ID = "cusine_id";
	public static String TABLE_MENUITEMS_COLUMN_CATEGORY_ID = "category_id";
	public static String TABLE_MENUITEMS_COLUMN_ITEM_ID = "item_id";
	
	public static String TABLE_ORDERDETAILS = "order_details";
	public static String TABLE_ORDERDETAILS_ORDERID = "order_id";
	public static String TABLE_ORDERDETAILS_RESTAURANTID = "restaurant_id";
	public static String TABLE_ORDERDETAILS_CONSUMERID = "consumer_id";
	public static String TABLE_ORDERDETAILS_TIMESTAMP = "timestamp";
	public static String TABLE_ORDERDETAILS_ORDERTOTAL = "ordder_total";
	public static String TABLE_ORDERDETAILS_INSTRUCTION = "instruction";
	
	public static String TABLE_ORDERMENUITEMS = "ordermenuitems";
	public static String TABLE_ORDERMENUITEMS_ORDERID = "order_id";
	public static String TABLE_ORDERMENUITEMS_ITEMID = "item_id";
	public static String TABLE_ORDERMENUITEMS_QUANTITY = "quantity";
	
	public static String TABLE_CONSUMER = "consumer";
	public static String TABLE_CONSUMER_COLUMN_NAME = "name";
	public static String TABLE_CONSUMER_COLUMN_EMAIL = "email";
	public static String TABLE_CONSUMER_COLUMN_ADDRESS = "address";
	public static String TABLE_CONSUMER_COLUMN_CITY = "city";
	public static String TABLE_CONSUMER_COLUMN_STATE = "state";
	public static String TABLE_CONSUMER_COLUMN_ZIPCODE = "zipcode";
	public static String TABLE_CONSUMER_COLUMN_PHONENUMBER = "phonenumber";
	public static String TABLE_CONSUMER_COLUMN_PASSWORD = "password";
	public static String TABLE_CONSUMER_COLUMN_GCMKEY = "gcm_reg_key";
	public static String TABLE_CONSUMER_COLUMN_OTPCODE = "otp_code";
	public static String TABLE_CONSUMER_COLUMN_CONSUMER_ID = "consumer_id";
	//-----------------------------------------------------------------------------------
	
	//SMS SPECIFIC CONSTANTS
	public static String SMS_USERNAME = "rachnakhokhar";
	public static String SMS_PASSWORD = "199159";
	public static String SMS_MESSAGE = "Kindly validate your OTP Code : ";
	public static String SMS_SENDERID = "WEBSMS";
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
	public static String JSON_RESTAURANT_ID = "restaurant_id";
	public static String JSON_OTP_CODE = "otpcode";
	public static String JSON_VALID_OTP_CODE = "valid otp";
	public static String JSON_BUSINESS_PROFILE_UPDATED = "profile updated sucessfully";
	public static String JSON_CONSUMER_PROFILE_UPDATED = "profile updated sucessfully";
	public static String JSON_MENUITEM_ADDED = "menuitem added successfully";
	public static String JSON_CATEGORY_ID = "category_id";
	public static String JSON_MENUITEM_ID = "item_id";
	public static String JSON_VALID_OTP_GENERATED = "otp generated";
	public static String JSON_BUSINESS_PASSWORD_RESET = "password reset sucessfully";
	public static String JSON_ITEM_ID = "item_id";
	public static String JSON_ORDER_ID = "order_id";
	public static String JSON_QUANTITY = "quantity";
	public static String JSON_CONSUMER_ID = "consumer_id";
	public static String JSON_TIMESTAMP = "timestamp";
	public static String JSON_INSTRUCTIONS = "instructions";
	public static String JSON_ORDERTOTAL = "order_total";
	public static String JSON_ORDER_ITEMS = "order_items";
	public static String JSON_ORDER_GENERATED = "New order generated";
	public static String JSON_ORDER = "ORDER";
	public static String JSON_GCM_KEY = "gcm_key";
	public static String JSON_ADDRESS = "address";
	public static String JSON_CATEGORY_UPDATED = "Category updated sucessfully";
	//-----------------------------------------------------------------------------------
	
	public static final int GENERIC_APP_ERROR_CODE = 5001;	
	
	//ERROR CONSTANTS
	public static String ERROR_GENERIC = "Incorrect Request Data.";
	public static String ERROR_SQL_QUERY_EXECUTION = "Error executing SQL query.";
	public static String ERROR_USER_ALREADY_REGISTERED = "User already registered with the number.";
	//-----------------------------------------------------------------------------------
}
