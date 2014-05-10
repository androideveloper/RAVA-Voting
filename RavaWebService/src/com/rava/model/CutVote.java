package com.rava.model;

public class CutVote {
	
	private int electId;
	private String answersSequence;
	private int answerId;
	
	public CutVote(int electId, String answersSequence, int answerId) {
		this.electId = electId;
		this.answersSequence = answersSequence;
		this.answerId = answerId;
	}

	public int getElectId() {
		return electId;
	}

	public void setElectId(int electId) {
		this.electId = electId;
	}

	public String getAnswersSequence() {
		return answersSequence;
	}

	public void setAnswersSequence(String answersSequence) {
		this.answersSequence = answersSequence;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
}
