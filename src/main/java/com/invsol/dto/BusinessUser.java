package com.invsol.dto;

public class BusinessUser {

	private int restaurant_id;
	private String phonenumber;

	public BusinessUser(int restaurant_id, String phonenumber) {
		super();
		this.restaurant_id = restaurant_id;
		this.phonenumber = phonenumber;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

}
