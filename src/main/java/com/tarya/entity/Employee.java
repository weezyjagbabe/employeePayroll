package com.tarya.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
/**
*
* @author SBortey
*/

@Document(collection = "employee")
public class Employee {

	@Id
	private String id;
	private String name;
	private String email;
	private String role;
	
	
	@PersistenceConstructor
	public Employee(String id, String name, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	

	public Employee() {
	}


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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	

}
