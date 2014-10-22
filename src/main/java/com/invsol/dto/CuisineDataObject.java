package com.invsol.dto;

public class CuisineDataObject {

	private int cuisine_id;
	private String cuisine_name;
	public CuisineDataObject(int cuisine_id, String cuisine_name) {
		super();
		this.cuisine_id = cuisine_id;
		this.cuisine_name = cuisine_name;
	}
	public int getCuisine_id() {
		return cuisine_id;
	}
	public String getCuisine_name() {
		return cuisine_name;
	}
	
	
}
