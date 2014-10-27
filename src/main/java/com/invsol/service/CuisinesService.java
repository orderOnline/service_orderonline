package com.invsol.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.CuisineData;
import com.invsol.dto.CuisineDataObject;
import com.invsol.errorhandling.AppException;

@Path("cuisines")
public class CuisinesService {

	@GET
	@Path("/all.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCuisines() {
		System.out.println("I am inside this method");
		CuisineData obj = new CuisineData();
		CuisineDataObject[] db_data = null;
		JSONObject finalResponseJson = new JSONObject();
		try {
			db_data = obj.getAllCuisines();

			JSONObject resultJson = new JSONObject();
			resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
			JSONArray cuisinesArray = new JSONArray();
			JSONObject tempCuisineObj = null;
			for (int i = 0; i < db_data.length; i++) {
				tempCuisineObj = new JSONObject();
				tempCuisineObj.put(AppConstants.JSON_CUISINE_ID, db_data[i].getCuisine_id());
				tempCuisineObj.put(AppConstants.JSON_CUISINE_NAME, db_data[i].getCuisine_name());
				cuisinesArray.put(tempCuisineObj);
			}
			
			Calendar calendar = Calendar.getInstance();
		    java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
		    System.out.println("timestamp=="+ourJavaTimestampObject);
			resultJson.put(AppConstants.JSON_RESPONSE, cuisinesArray);
			finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
	}

}
