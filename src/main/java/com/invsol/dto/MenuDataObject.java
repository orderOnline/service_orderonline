package com.invsol.dto;

public class MenuDataObject {
	private int item_id;
	private String item_name;
	private int price;
	private int category_id;
	public MenuDataObject(int item_id, String item_name, int price, int category_id) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.price = price;
		this.category_id = category_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public int getPrice() {
		return price;
	}
	public int getCategory_id() {
		return category_id;
	}
	
	

}
