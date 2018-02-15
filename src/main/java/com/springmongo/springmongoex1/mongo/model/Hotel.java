/**
 * 
 */
package com.springmongo.springmongoex1.mongo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author 372153
 *
 */
@Document(collection="Hotel")
public class Hotel {

	@Id
	private String id;

	@Field(value = "name")
	private String name;

	@Indexed(direction = IndexDirection.ASCENDING)
	private int stayCost;
	private Address address;
	private List<Review> reviews;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStayCost() {
		return stayCost;
	}
	public void setStayCost(int stayCost) {
		this.stayCost = stayCost;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
}
