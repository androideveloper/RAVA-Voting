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
	//private int state;
	private String creatorId;
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
	
	public Election(int id, String name, String description, ElectionState state, String creatorId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.state = state;
		this.creatorId = creatorId;
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

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
		
}
