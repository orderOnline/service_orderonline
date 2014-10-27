package com.invsol.dto;

import org.codehaus.jettison.json.JSONArray;

public class OrderDataObject {
	
	private String consumer_name, instructions, address;
	private String timestamp;
	private JSONArray itemsArray;
	
	public OrderDataObject(){
		
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public JSONArray getItemsArray() {
		return itemsArray;
	}

	public void setItemsArray(JSONArray itemsArray) {
		this.itemsArray = itemsArray;
	}

	
}
