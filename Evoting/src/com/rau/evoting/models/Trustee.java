package com.rau.evoting.models;

public class Trustee {
	private int id;
	private String name;
	private String email;
	private boolean generated;
	
	public Trustee(int id, String name, String email, boolean isGenerated) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.generated = isGenerated;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isGenerated() {
		return generated;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
}
