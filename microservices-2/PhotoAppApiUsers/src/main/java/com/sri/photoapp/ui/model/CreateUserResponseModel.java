package com.sri.photoapp.ui.model;

public class CreateUserResponseModel {

	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	public CreateUserResponseModel() {
		super();
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "CreateUserResponseModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", userId=" + userId + "]";
	}
	
	
	
	
}
