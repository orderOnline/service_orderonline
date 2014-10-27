package com.invsol.service;

import java.io.BufferedReader;
import java.io.IOException;
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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.invsol.constants.AppConstants;
import com.invsol.dao.ForgotPasswordImpl;
import com.invsol.dao.OrdersImpl;
import com.invsol.dto.OrderDataObject;
import com.invsol.errorhandling.AppException;

@Path("orders")
public class OrdersService {
	
	@GET
	@Path("/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrders(@PathParam("id") String orderID) {
		return ("orderID is=="+orderID);
	}

	@POST
	@Path("/new.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(InputStream incomingData) throws AppException{
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
			JSONObject orderData = new JSONObject(crunchifyBuilder.toString());
			OrdersImpl newOrder = new OrdersImpl();
			int newOrderIDCreated = newOrder.createNewOrder(orderData.getInt(AppConstants.JSON_RESTAURANT_ID), 
					orderData.getInt(AppConstants.JSON_CONSUMER_ID), orderData.getLong(AppConstants.JSON_TIMESTAMP), 
					orderData.getString(AppConstants.JSON_INSTRUCTIONS), orderData.getInt(AppConstants.JSON_ORDERTOTAL), 
					orderData.getJSONArray(AppConstants.JSON_ORDER_ITEMS));
			if (newOrderIDCreated > -1) {
				JSONObject resultJson = new JSONObject();
				resultJson.put(AppConstants.JSON_TYPE, AppConstants.JSON_TYPE_SUCCESS);
				resultJson.put(AppConstants.JSON_RESPONSE, AppConstants.JSON_ORDER_GENERATED);
				finalResponseJson.put(AppConstants.JSON_RESULT, resultJson);
				
				OrderDataObject finalData = newOrder.getOrderGCMDetails(newOrderIDCreated);
				JSONObject gcmMSG = new JSONObject();
				gcmMSG.put("name", finalData.getConsumer_name());
				gcmMSG.put("address", finalData.getAddress());
				gcmMSG.put("instructions", finalData.getInstructions());
				gcmMSG.put("timestamp", finalData.getTimestamp());
				gcmMSG.put("items", finalData.getItemsArray());
				
				String regId = newOrder.getBusinessGCMRegKey(orderData.getInt(AppConstants.JSON_RESTAURANT_ID));
				Sender sender = new Sender("AIzaSyCgRv83Uwq5QD0jtwD98vKPCC9iSAd4okg");
				Message message = new Message.Builder().timeToLive(30)
						.delayWhileIdle(true).addData(AppConstants.JSON_ORDER, gcmMSG.toString()).build();
				Result result = sender.send(message, regId, 1);
			}
		} catch (JSONException e) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.ERROR_GENERIC,
					e.getMessage(), "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return HTTP response 200 in case of success
		return Response.status(200).entity(finalResponseJson.toString()).build();
 
	}
	
	@PUT
	@Path("/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrder(@PathParam("id") String orderID, InputStream incomingData) {
 
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
	
	@DELETE
	@Path("/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteOrder(@PathParam("id") String orderID) {
 
		return ("orderID is=="+orderID);
        
	}
	
}
