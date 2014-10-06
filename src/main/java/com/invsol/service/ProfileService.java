package com.invsol.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.invsol.model.Sample;

@Path("profile")
public class ProfileService {

	@GET
	@Path("/business/{id}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Sample getBusinessUserProfile(@PathParam("id") String userName) {
 
		Sample track = new Sample();
		track.setTitle("username is=="+userName);
		track.setSinger("Khokhar");
 
		return track;
 
	}
	
	@PUT
	@Path("/business")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Sample track) {
 
		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
 
	}
	
}
