package com.rau.evoting.models;

public class Answer {
	private int id;
	private String answer;
	private int numberOfVotes; 	
		
	public Answer() {
	}
	
	public Answer(int id, String answer) {
		this.id = id;
		this.answer = answer;
	}
	
	public Answer(int id, String answer, int numberOfVotes) {
		this.id = id;
		this.answer = answer;
		this.numberOfVotes = numberOfVotes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
	public void incNumberOfVotes(){
		++numberOfVotes;
	}

}
