package com.rau.evoting.models;

import java.util.ArrayList;

/**
 * @author Aram
 * 
 */
public class Election {

	private int id;
	private String name;
	private String description;
	private ElectionState state;
	// private int state;
	private int creatorId;
	private String publicKey;
	private int mixStage;
	private ArrayList<Answer> answers;

	public Election(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Election(int id, String name, String description, ElectionState state) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.state = state;
	}

	public Election(int id, String name, String description,
			ElectionState state, int creatorId, String key, int mixStage) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.state = state;
		this.creatorId = creatorId;
		this.publicKey = key;
		this.mixStage = mixStage;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ElectionState getState() {
		return state;
	}

	public void setState(ElectionState state) {
		this.state = state;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public int getMixStage() {
		return mixStage;
	}

	public void setMixStage(int mixStage) {
		this.mixStage = mixStage;
	}

}
