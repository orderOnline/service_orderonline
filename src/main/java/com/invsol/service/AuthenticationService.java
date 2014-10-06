package com.invsol.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.invsol.model.Sample;

@Path("authenticate")
public class AuthenticationService {

	@GET
	@Path("/business")
	@Produces(MediaType.APPLICATION_JSON)
	public Sample getTrackInJSON() {
 
		Sample track = new Sample();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
 
		return track;
 
	}
	
}
