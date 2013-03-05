package com.rau.evoting.models;

import java.util.ArrayList;

public class Question {
	private String question;
	private ArrayList<String> answers;
	
	public Question() {
		
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	
}
