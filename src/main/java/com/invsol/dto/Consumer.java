package com.invsol.dto;

public class Consumer {
	
	private int consumer_id, zipcode;
	private long phonenumber;
	private String name, address, city, state, email;
	public Consumer(int consumer_id, long phonenumber) {
		super();
		this.consumer_id = consumer_id;
		this.phonenumber = phonenumber;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
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
	public int getConsumer_id() {
		return consumer_id;
	}
	public long getPhonenumber() {
		return phonenumber;
	}

	
}
