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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.AuthenticateUser;
import com.invsol.dto.BusinessUser;
import com.invsol.errorhandling.AppException;

@Path("authorize")
public class AuthorizationService {

	@POST
	@Path("/business.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authorizeBusinessUser(InputStream incomingData) throws AppException {
		System.out.println("I am inside authorize business user.");
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
			AuthenticateUser authenticateUser = new AuthenticateUser();
			BusinessUser businessUserData = authenticateUser.authenticateBusinessUser(
					userData.getString(AppConstants.JSON_PHONENUMBER), userData.getString(AppConstants.JSON_PASSWORD));
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
			AuthenticateUser authenticateUser = new AuthenticateUser();
			boolean isTokenValid = authenticateUser
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
}
