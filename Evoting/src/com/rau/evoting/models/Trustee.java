package com.rau.evoting.models;

public class Trustee {
	private int id;
	private String email;
	private boolean generated;
	private String publicKey;
	private int electId;
	private String token;
	
	public Trustee(int id, String email, boolean isGenerated, String token) {
		this.id = id;
		this.email = email;
		this.generated = isGenerated;
		this.token = token;
	}
	
	public Trustee(int id, String email, boolean isGenerated, String publicKey, int electId) {
		this.id = id;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
	}
	
	public Trustee(int id, String email, boolean isGenerated, String publicKey, int electId, String token) {
		this.id = id;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
