package com.rau.evoting.models;

import java.util.Date;

public class Candidate {
	private int id;
	private String name;
	private String surname;
	private String email;
	private Date dateOfBirth;

	
	public Candidate(){
		
	}
	
	public Candidate(int id, String name, String surname,String email ,Date dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
