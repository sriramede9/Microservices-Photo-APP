package com.sri.photoapp.ui.controllers.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserModel {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@Email
	private String email;
	@NotNull
	private String password;

	public UserModel() {
		super();
	}

	public UserModel(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + "]";
	}

}
