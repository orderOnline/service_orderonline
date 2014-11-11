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
import com.invsol.dao.LoginUser;
import com.invsol.dto.BusinessUser;
import com.invsol.errorhandling.AppException;

/**
 * Class for Login Implementation
 * @author rkhokhar
 *
 */
@Path("authenticate")
public class LoginService {

	@POST
	@Path("/business.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateBusinessUser(InputStream incomingData) throws AppException {
		System.out.println("I am inside authenticate business user.");
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
			LoginUser loginUser = new LoginUser();
			BusinessUser businessUserData = loginUser.authenticateBusinessUser(userData.getLong(AppConstants.JSON_PHONENUMBER), userData.getString(AppConstants.JSON_PASSWORD));
			JSONObject resultJson = new JSONObject();
			resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
			JSONObject businessUserJSON = new JSONObject();
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_RESTAURANT_ID,
					businessUserData.getRestaurant_id());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_PHONENUMBER, businessUserData.getPhonenumber());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_NAME, businessUserData.getName());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_EMAIL, businessUserData.getEmail());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_ADDRESS, businessUserData.getAddress());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_CITY, businessUserData.getCity());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_STATE, businessUserData.getState());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME, businessUserData.getService_start_time());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME, businessUserData.getService_end_time());
			businessUserJSON.put(AppConstants.TABLE_RESTAURANT_COLUMN_ZIPCODE, businessUserData.getZipcode());
			resultJson.put(AppConstants.JSON_RESPONSE, businessUserJSON);
			finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
 
	}
}
