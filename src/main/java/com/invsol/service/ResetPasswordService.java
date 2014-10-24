package com.invsol.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.ForgotPasswordImpl;
import com.invsol.dao.ResetPasswordImpl;
import com.invsol.errorhandling.AppException;

@Path("resetpassword")
public class ResetPasswordService {

	@PUT
	@Path("/business.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPasswordImpl(InputStream incomingData) throws AppException {
		System.out.println("I am inside forgotpwd business user.");
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
			ResetPasswordImpl resetPwd = new ResetPasswordImpl();
			boolean isTokenValid = resetPwd
					.resetPasswordRestaurant(userData.getLong(AppConstants.JSON_PHONENUMBER),userData.getInt(AppConstants.JSON_OTP_CODE),userData.getString(AppConstants.JSON_PASSWORD));
			if (isTokenValid) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_BUSINESS_PASSWORD_RESET);
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
