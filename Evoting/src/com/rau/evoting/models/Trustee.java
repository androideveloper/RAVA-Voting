package com.rau.evoting.models;

public class Trustee {
	private String id;
	private String name;
	private String email;
	private boolean generated;
	private String publicKey;
	private int electId;
	
	public Trustee(String id, String email, boolean isGenerated) {
		this.id = id;
		this.email = email;
		this.generated = isGenerated;
	}
	
	public Trustee(String id, String email, boolean isGenerated, String publicKey, int electId) {
		this.id = id;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
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

	public boolean isGenerated() {
		return generated;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public int getElectId() {
		return electId;
	}

	public void setElectId(int electId) {
		this.electId = electId;
	}
	
}
