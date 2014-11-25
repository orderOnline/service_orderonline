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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.dao.MenuItemData;
import com.invsol.dto.MenuDataObject;
import com.invsol.errorhandling.AppException;

@Path("menuitem")
public class MenuItemService {
	
	@PUT
	@Path("/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenuItem(@PathParam("id") String restaurantID, InputStream incomingData) {
 
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
	@Path("/{category_id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuItem(@PathParam("category_id") String categoryID, InputStream incomingData) throws AppException {
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
			JSONObject menuItemData = new JSONObject(crunchifyBuilder.toString());
			MenuItemData objMenuItem = new MenuItemData();
			MenuDataObject newAddedMenu = objMenuItem.addMenuItem(menuItemData.getInt(AppConstants.TABLE_MENUITEMS_COLUMN_CUISINE_ID),Integer.parseInt(categoryID), menuItemData.getString(AppConstants.TABLE_MENUITEMS_COLUMN_NAME), menuItemData.getInt(AppConstants.TABLE_MENUITEMS_COLUMN_PRICE));
			if(newAddedMenu != null){
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_MENUITEM_ID, newAddedMenu.getItem_id());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
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
	public String deleteMenuItem(@PathParam("id") String menuitemID) {
 
		return ("menuitemID is=="+menuitemID);
        
	}

}
