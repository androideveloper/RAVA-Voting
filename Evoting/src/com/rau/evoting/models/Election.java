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
	private boolean open;
	private ArrayList<Answer> answers;
		
	public Election(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Election(int id, String name, String description, boolean open) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.open = open;
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
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
		
}
