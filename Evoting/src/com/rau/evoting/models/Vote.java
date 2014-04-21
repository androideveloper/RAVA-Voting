package com.rau.evoting.models;

import java.math.BigInteger;

public class Vote {

	private int id;
	private int electId;
	private int voterId;
	private int auditBallot;
	private String encoded1;
	private String encoded2;
	private String auditSequence;
	private int answerId;
	private String chaumPedersen1;
	private String chaumPedersen2;
	
	public Vote(int id, int electId, int voterId, int auditBallot,
			String encoded1, String encoded2, String auditSequence, int answerId, 
			String chaumPedersen1, String chaumPedersen2) {
		this.id = id;
		this.electId = electId;
		this.voterId = voterId;
		this.auditBallot = auditBallot;
		this.encoded1 = encoded1;
		this.encoded2 = encoded2;
		this.auditSequence = auditSequence;
		this.answerId = answerId;
		this.chaumPedersen1 = chaumPedersen1;
		this.chaumPedersen2 = chaumPedersen2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getElectId() {
		return electId;
	}

	public void setElectId(int electId) {
		this.electId = electId;
	}

	public int getVoterId() {
		return voterId;
	}

	public void setVoterId(int voterId) {
		this.voterId = voterId;
	}

	public int getAuditBallot() {
		return auditBallot;
	}

	public void setAuditBallot(int auditBallot) {
		this.auditBallot = auditBallot;
	}

	public String getEncoded1() {
		return encoded1;
	}

	public void setEncoded1(String encoded1) {
		this.encoded1 = encoded1;
	}

	public String getEncoded2() {
		return encoded2;
	}

	public void setEncoded2(String encoded2) {
		this.encoded2 = encoded2;
	}

	public String getAuditSequence() {
		return auditSequence;
	}

	public void setAuditSequence(String auditSequence) {
		this.auditSequence = auditSequence;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getChaumPedersen1() {
		return chaumPedersen1;
	}

	public void setChaumPedersen1(String chaumPedersen1) {
		this.chaumPedersen1 = chaumPedersen1;
	}

	public String getChaumPedersen2() {
		return chaumPedersen2;
	}

	public void setChaumPedersen2(String chaumPedersen2) {
		this.chaumPedersen2 = chaumPedersen2;
	}	
	
}
