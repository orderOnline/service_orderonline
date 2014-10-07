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

@Path("profile")
public class ProfileService {

	@GET
	@Path("/business/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBusinessUserProfile(@PathParam("id") String restaurantID) {
 
		return ("restaurantID is=="+restaurantID);
 
	}
	
	@PUT
	@Path("/business/{id}.json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBusinessUserProfile(@PathParam("id") String restaurantID, InputStream incomingData) {
 
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
	
}
