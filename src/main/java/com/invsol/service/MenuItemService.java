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
import com.invsol.dao.CategoryData;
import com.invsol.dao.MenuItemData;
import com.invsol.dto.CategoryDataObject;
import com.invsol.dto.MenuDataObject;
import com.invsol.errorhandling.AppException;

@Path("menuitem")
public class MenuItemService {
	
	@GET
	@Path("/{category_id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuItems(@PathParam("id") String categoryID) {
		System.out.println("I am inside this method");
		MenuItemData obj = new MenuItemData();
		MenuDataObject[] db_data = null;
		JSONObject finalResponseJson = new JSONObject();
		try {
			db_data = obj.getMenuItems(Integer.parseInt(categoryID));

			JSONObject resultJson = new JSONObject();
			resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
			JSONArray menuitemsArray = new JSONArray();
			JSONObject tempMenuItemObj = null;
			for (int i = 0; i < db_data.length; i++) {
				tempMenuItemObj = new JSONObject();
				tempMenuItemObj.put(AppConstants.JSON_MENUITEM_ID, db_data[i].getItem_id());
				tempMenuItemObj.put(AppConstants.JSON_MENUITEM_NAME, db_data[i].getItem_name());
				tempMenuItemObj.put(AppConstants.JSON_MENUITEM_PRICE, db_data[i].getPrice());
				menuitemsArray.put(tempMenuItemObj);
			}
			resultJson.put(AppConstants.JSON_RESPONSE, menuitemsArray);
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
	public Response updateMenuItem(@PathParam("id") String menuItemID, InputStream incomingData) {
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
		System.out.println("Category Data Received: " + crunchifyBuilder.toString());
		try {
			JSONObject menuItemData = new JSONObject(crunchifyBuilder.toString());
			MenuItemData obj = new MenuItemData();
			boolean isMenuItemupdated = obj.updateMenuItem(Integer.parseInt(menuItemID), 
					menuItemData.getString(AppConstants.JSON_MENUITEM_NAME),
					menuItemData.getInt(AppConstants.JSON_MENUITEM_PRICE));
			if (isMenuItemupdated) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_MENUITEM_UPDATED);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
			}
		} catch (JSONException e) {
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
        return Response.status(200).entity(crunchifyBuilder.toString()).build();
 
	}
	
	@POST
	@Path("/{category_id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuItem(@PathParam("category_id") String categoryID, InputStream incomingData){
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
			MenuDataObject newAddedMenu = objMenuItem.addMenuItem(Integer.parseInt(categoryID), menuItemData.getString(AppConstants.TABLE_MENUITEMS_COLUMN_NAME), menuItemData.getInt(AppConstants.TABLE_MENUITEMS_COLUMN_PRICE));
			if(newAddedMenu != null){
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				JSONObject tempCategoryObj = new JSONObject();
				tempCategoryObj.put(AppConstants.JSON_MENUITEM_ID, newAddedMenu.getItem_id());
				resultJson.put(AppConstants.JSON_RESPONSE, tempCategoryObj);
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
	public Response deleteMenuItem(@PathParam("id") String menuitemID) {
		JSONObject finalResponseJson = new JSONObject();
		try {
			MenuItemData objMenuItem = new MenuItemData();
			boolean isMenuItemDeleted = objMenuItem.deleteMenuItem(Integer.parseInt(menuitemID));
			if (isMenuItemDeleted) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_MENUITEM_DELETED);
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
