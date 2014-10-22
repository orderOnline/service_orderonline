package com.invsol.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.ProfileData;
import com.invsol.errorhandling.AppException;

@Path("profile")
public class ProfileService {

	@GET
	@Path("/business/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBusinessUserProfile(@PathParam("id") String restaurantID) {

		return ("restaurantID is==" + restaurantID);

	}

	@PUT
	@Path("/business/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBusinessUserProfile(@PathParam("id") String restaurantID, InputStream incomingData)
			throws AppException {
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
		System.out.println("Profile Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject profileData = new JSONObject(crunchifyBuilder.toString());
			JSONArray closedArr = profileData.getJSONArray(AppConstants.TABLE_RESTAURANT_COLUMN_CLOSEDON);
			
			Integer[] closedOnArray = new Integer[closedArr.length()];
			for (int i = 0; i < closedOnArray.length; i++) {
				closedOnArray[i] = closedArr.optInt(i);
			}
			
			ProfileData profileDAO = new ProfileData();
			boolean isProfileUpdated = profileDAO.updateBusinessUserProfile(Integer.parseInt(restaurantID), profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_NAME),
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_EMAIL), 
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_STARTTIME), 
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_SERVICE_ENDTIME), 
					closedOnArray,
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_ADDRESS), 
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_CITY), 
					profileData.getString(AppConstants.TABLE_RESTAURANT_COLUMN_STATE), 
					profileData.getInt(AppConstants.TABLE_RESTAURANT_COLUMN_ZIPCODE),
					closedOnArray);
			if (isProfileUpdated) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_BUSINESS_PROFILE_UPDATED);
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
