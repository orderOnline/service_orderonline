package com.invsol.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.RegisterUser;
import com.invsol.dto.BusinessUser;
import com.invsol.dto.Consumer;
import com.invsol.errorhandling.AppException;

/**
 * Class for Registration Implementation
 * @author rkhokhar
 *
 */
@Path("register")
public class RegistrationService {

	@POST
	@Path("/business.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authorizeBusinessUser(InputStream incomingData) throws AppException {
		System.out.println("I am inside register business user.");
		JSONObject finalResponseJson = new JSONObject();
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject userData = new JSONObject(crunchifyBuilder.toString());
			RegisterUser registerUser = new RegisterUser();
			BusinessUser businessUserData = registerUser.registerBusinessUser(
					userData.getLong(AppConstants.JSON_PHONENUMBER), userData.getString(AppConstants.JSON_PASSWORD), userData.getString(AppConstants.JSON_GCM_KEY));
			JSONObject resultJson = new JSONObject();
			resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
			JSONObject businessUserJSON = new JSONObject();
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID,
					businessUserData.getRestaurant_id());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER, businessUserData.getPhonenumber());
			resultJson.put(AppConstants.JSON_RESPONSE, businessUserJSON);
			finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}

	@POST
	@Path("/business/validateotp.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateOTPCode(InputStream incomingData) throws AppException {
		System.out.println("I am inside validate otp business user.");
		JSONObject finalResponseJson = new JSONObject();
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject userData = new JSONObject(crunchifyBuilder.toString());
			RegisterUser authorizeUser = new RegisterUser();
			boolean isTokenValid = authorizeUser
					.validateotpBusinessUser(userData.getString(AppConstants.JSON_RESTAURANT_ID),
							userData.getString(AppConstants.JSON_OTP_CODE));
			if (isTokenValid) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_VALID_OTP_CODE);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}
	//----------------------------------------------------------------------------------------------------------------
	
	@POST
	@Path("/consumer.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerConsumer(InputStream incomingData) throws AppException {
		System.out.println("I am inside register consumer user.");
		JSONObject finalResponseJson = new JSONObject();
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject userData = new JSONObject(crunchifyBuilder.toString());
			RegisterUser registerUser = new RegisterUser();
			Consumer consumerData = registerUser.registerConsumer(
					userData.getLong(AppConstants.JSON_PHONENUMBER), userData.getString(AppConstants.JSON_PASSWORD), userData.getString(AppConstants.JSON_GCM_KEY));
			JSONObject resultJson = new JSONObject();
			resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
			JSONObject userJSON = new JSONObject();
			userJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID,
					consumerData.getConsumer_id());
			userJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER, consumerData.getPhonenumber());
			resultJson.put(AppConstants.JSON_RESPONSE, userJSON);
			finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}

	@POST
	@Path("/consumer/validateotp.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateConsumerOTPCode(InputStream incomingData) throws AppException {
		System.out.println("I am inside validate otp consumer user.");
		JSONObject finalResponseJson = new JSONObject();
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject userData = new JSONObject(crunchifyBuilder.toString());
			RegisterUser authorizeUser = new RegisterUser();
			boolean isTokenValid = authorizeUser
					.validateotpConsumer(userData.getString(AppConstants.JSON_CONSUMER_ID),
							userData.getString(AppConstants.JSON_OTP_CODE));
			if (isTokenValid) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_VALID_OTP_CODE);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}
}
