package com.rau.evoting.models;

public class CutVote {
	
	private int electId;
	private String answersSequence;
	private int answerId;
	private int mixStage;
	
	public CutVote(int electId, String answersSequence, int answerId, int mixStage) {
		this.electId = electId;
		this.answersSequence = answersSequence;
		this.answerId = answerId;
		this.mixStage = mixStage;
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

	public int getMixStage() {
		return mixStage;
	}

	public void setMixStage(int mixStage) {
		this.mixStage = mixStage;
	}
		
}
