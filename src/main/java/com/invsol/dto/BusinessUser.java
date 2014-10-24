package com.invsol.dto;

public class BusinessUser {

	private int restaurant_id, zipcode, min_order;
	private long phonenumber;
	private String name, address, city, state, email;
	private String service_start_time, service_end_time;
	private int[] closedOn;

	public BusinessUser(int restaurant_id, long phonenumber) {
		super();
		this.restaurant_id = restaurant_id;
		this.phonenumber = phonenumber;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public int getMin_order() {
		return min_order;
	}

	public void setMin_order(int min_order) {
		this.min_order = min_order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getService_start_time() {
		return service_start_time;
	}

	public void setService_start_time(String service_start_time) {
		this.service_start_time = service_start_time;
	}

	public String getService_end_time() {
		return service_end_time;
	}

	public void setService_end_time(String service_end_time) {
		this.service_end_time = service_end_time;
	}

	public int[] getClosedOn() {
		return closedOn;
	}

	public void setClosedOn(int[] closedOn) {
		this.closedOn = closedOn;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}

	
}
