package com.invsol.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.invsol.model.Sample;

@Path("authorize")
public class AuthorizationService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Sample getTrackInJSON() {
 
		Sample track = new Sample();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
 
		return track;
 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Sample track) {
 
		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
 
	}
}
