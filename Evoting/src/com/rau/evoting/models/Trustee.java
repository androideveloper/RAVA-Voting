package com.rau.evoting.models;

public class Trustee {
	private int id;
	private int userId;
	private String email;
	private boolean generated;
	private String publicKey;
	private int electId;
	private String token;
	private boolean decoded;
	private boolean mixDone;
	private int mixServer;

	public Trustee(int userId, String email, boolean isGenerated, String token,
			boolean mixDone, int mixServer) { System.out.println("mix server: " + mixServer );
		this.userId = userId;
		this.email = email;
		this.generated = isGenerated;
		this.token = token;
		this.mixDone = mixDone;
		this.mixServer = mixServer;
	}

	public Trustee(int userId, String email, boolean isGenerated,
			String publicKey, int electId) {
		this.userId = userId;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
	}

	public Trustee(int userId, String email, boolean isGenerated,
			String publicKey, int electId, String token) {
		this.userId = userId;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
		this.token = token;
	}

	public Trustee(int id, int userId, String email, boolean isGenerated,
			String publicKey, int electId, String token) {
		this.id = id;
		this.userId = userId;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
		this.token = token;
	}

	public Trustee(int id, int userId, String email, boolean isGenerated,
			String publicKey, int electId, String token, boolean decoded,
			boolean mixDone, int mixServer) {
		this.id = id;
		this.userId = userId;
		this.email = email;
		this.generated = isGenerated;
		this.publicKey = publicKey;
		this.electId = electId;
		this.token = token;
		this.decoded = decoded;
		this.mixDone = mixDone;
		this.mixServer = mixServer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public boolean isDecoded() {
		return decoded;
	}

	public void setDecoded(boolean decoded) {
		this.decoded = decoded;
	}

	public boolean isMixDone() {
		return mixDone;
	}

	public void setMixDone(boolean mixDone) {
		this.mixDone = mixDone;
	}

	public int getMixServer() {
		return mixServer;
	}

	public void setMixServer(int mixServer) {
		this.mixServer = mixServer;
	}

}
