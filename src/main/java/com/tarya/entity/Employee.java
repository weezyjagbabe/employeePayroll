package com.tarya.entity;

import javax.validation.constraints.NotBlank;

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
	public Employee(@NotBlank(message = "Id may not be blank") String id,
			@NotBlank(message = "Name may not be blank") String name,
			@NotBlank(message = "Email may not be blank") String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public Employee(@NotBlank(message = "Name may not be blank") String name,
			@NotBlank(message = "Email may not be blank") String email, String role) {
		super();
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
