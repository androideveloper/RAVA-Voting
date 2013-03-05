package com.rau.evoting.models;

public class User {
	private String username;
	private String password;
	private int id;
	private String name;
	private String surname;
	
	public User(){
		
	}
	
	public User(String username, String password, int id, String name,
			String surname) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
