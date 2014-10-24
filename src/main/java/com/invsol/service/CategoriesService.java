package com.invsol.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.invsol.dao.CategoryData;
import com.invsol.dto.CategoryDataObject;
import com.invsol.errorhandling.AppException;

@Path("categories")
public class CategoriesService {
	
	@PUT
	@Path("/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenuCategory(@PathParam("id") String restaurantID, InputStream incomingData) {
 
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
 
        // return HTTP response 200 in case of success
        return Response.status(200).entity(crunchifyBuilder.toString()).build();
 
	}
	
	@POST
	@Path("/{restaurant_id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuCategory(@PathParam("restaurant_id") String restaurantID, InputStream incomingData) throws AppException {
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
        System.out.println("Authenticate Data Received: " + crunchifyBuilder.toString());
        try {
			JSONObject categoryData = new JSONObject(crunchifyBuilder.toString());
			CategoryData objCategory = new CategoryData();
			boolean isCategoryAdded = objCategory.addNewCategory(Integer.parseInt(restaurantID), categoryData.getString(AppConstants.TABLE_CATEGORY_COLUMN_CATEGORY_NAME));
			if(isCategoryAdded){
				CategoryDataObject[] db_data = objCategory.getCategories(Integer.parseInt(restaurantID));
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				JSONArray categoriesArray = new JSONArray();
				JSONObject tempCategoryObj = null;
				for (int i = 0; i < db_data.length; i++) {
					tempCategoryObj = new JSONObject();
					tempCategoryObj.put(AppConstants.JSON_CUISINE_ID, db_data[i].getCategory_id());
					tempCategoryObj.put(AppConstants.JSON_CUISINE_NAME, db_data[i].getCategory_name());
					categoriesArray.put(tempCategoryObj);
				}
				resultJson.put(AppConstants.JSON_RESPONSE, categoriesArray);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
 
	}
	
	@DELETE
	@Path("/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteMenuCategory(@PathParam("id") String categoryID) {
 
		return ("categoryID is=="+categoryID);
        
	}

}
