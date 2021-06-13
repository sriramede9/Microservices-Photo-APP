package com.sri.photoapp.ui.model;

public class AlbumResponseModel {

	private String albumId;
	private String userId;
	private String name;
	private String description;

	public AlbumResponseModel() {
		super();
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AlbumResponseModel [albumId=" + albumId + ", userId=" + userId + ", name=" + name + ", description="
				+ description + "]";
	}

	public AlbumResponseModel(String albumId, String userId, String name, String description) {
		super();
		this.albumId = albumId;
		this.userId = userId;
		this.name = name;
		this.description = description;
	}

}
