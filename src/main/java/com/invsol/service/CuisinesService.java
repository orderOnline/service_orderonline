package com.invsol.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
			resultJson.put(AppConstants.JSON_RESPONSE, cuisinesArray);
			finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
		}catch (JSONException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} catch (NumberFormatException e) {
		} catch (AppException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
	}

	@PUT
	@Path("/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCuisine(@PathParam("id") String cuisineID, InputStream incomingData){
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
		System.out.println("Cuisine Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject cuisineData = new JSONObject(crunchifyBuilder.toString());
			CuisineData objCuisine = new CuisineData();
			boolean isCuisineupdated = objCuisine.updateCuisine(Integer.parseInt(cuisineID), cuisineData.getString(AppConstants.JSON_CUISINE_NAME));
			if (isCuisineupdated) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_CUISINE_UPDATED);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		}catch (JSONException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} catch (NumberFormatException e) {
		} catch (AppException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} 

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}

	@POST
	@Path("/add.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCuisine(InputStream incomingData) {
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
		System.out.println("Cuisine Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject cuisineData = new JSONObject(crunchifyBuilder.toString());
			CuisineData objCuisine = new CuisineData();
			CuisineDataObject newAddCuisine = objCuisine.addNewCuisine(cuisineData.getString(AppConstants.JSON_CUISINE_NAME));
			if (newAddCuisine != null) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				JSONObject tempCuisineObj = new JSONObject();
				tempCuisineObj.put(AppConstants.JSON_CUISINE_ID, newAddCuisine.getCuisine_id());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCuisineObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		}catch (JSONException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} catch (NumberFormatException e) {
		} catch (AppException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} 

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();

	}

	@DELETE
	@Path("/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCuisine(@PathParam("id") String cuisineID){
		JSONObject finalResponseJson = new JSONObject();
		try {
			CuisineData objCuisine = new CuisineData();
			boolean isCuisineDeleted = objCuisine.deleteCuisine(Integer.parseInt(cuisineID));
			if (isCuisineDeleted) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_CUISINE_DELETED);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		}catch (JSONException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} catch (NumberFormatException e) {
		} catch (AppException e) {
			try{
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_ERROR);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_TYPE_ERROR_MESSAGE, e.getMessage());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}catch( JSONException ex){
				
			}
		} 

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
	}
}
