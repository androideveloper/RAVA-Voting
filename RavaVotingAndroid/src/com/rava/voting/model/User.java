package com.rava.voting.model;

public class User {

	private int id;
	private String fbId;
	private String email;

	public User() {

	}

	public User(int id, String fbId, String email) {
		super();
		this.id = id;
		this.fbId = fbId;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
