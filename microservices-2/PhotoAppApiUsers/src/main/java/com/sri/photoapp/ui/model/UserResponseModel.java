package com.sri.photoapp.ui.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponseModel {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<AlbumResponseModel> albums = new ArrayList<AlbumResponseModel>();

	public UserResponseModel() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "UserResponseModel [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", albums=" + albums + "]";
	}

}
