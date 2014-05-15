package com.rau.evoting.beans;

import java.util.ArrayList;

import com.rau.evoting.data.ElectionAnswerDP;
import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.models.Answer;
import com.rau.evoting.models.Election;

public class CountedElection {
	
	private int elId;
	private Election election;
	private ArrayList<Answer> answers;
	
	public CountedElection() {
		
	}
	
	public int getElId() {
		return elId;
	}

	public void setElId(int elId) {
		this.elId = elId;
	}

	public Election getElection() {
		election = ElectionDP.getElection(elId);
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}

	public ArrayList<Answer> getAnswers() {
		answers = ElectionAnswerDP.getElectionAnswers(elId);
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
}
