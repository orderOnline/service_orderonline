package com.invsol.dto;

public class CategoryDataObject {

	private int category_id;
	private String category_name;
	public CategoryDataObject(int category_id, String category_name) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
	}
	public int getCategory_id() {
		return category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	
	
}
