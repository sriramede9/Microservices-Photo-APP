package com.sri.photoapp.ui.controllers.model;

public class LoginRequestModel {

	private String email;
	private String password;

	public LoginRequestModel() {
		super();
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
		return "LoginRequestModel [email=" + email + ", password=" + password + "]";
	}

}
