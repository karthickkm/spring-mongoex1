package com.springmongo.springmongoex1.mongo.model;

public class Address {

	private int no;
	private String street;

	public Address() { }
	
	public Address(int no, String street) {
		this.no = no;
		this.street = street;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}	

}
