package com.rau.evoting.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Answer;
import com.rau.evoting.utils.Util;

public class Vote {
	
	private ArrayList<Answer> answers;
	private ArrayList<Integer> a1;
	private ArrayList<Integer> a2;
	private boolean showEncode;
	
	public Vote() {
		
	}
	
	public String fromElection() {
		int elId = Integer.valueOf(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("elId"));
		answers = SqlDataProvider.getInstance().getElectionAnswers(elId);
		showEncode = false;
		a1 = new ArrayList<Integer>();
		a2 = new ArrayList<Integer>();
		for(Answer ans : answers) {
			a1.add(ans.getId());
			a2.add(ans.getId());
		}
		return "Vote";
	}
	
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public ArrayList<Integer> getA1() {
		return a1;
	}

	public void setA1(ArrayList<Integer> a1) {
		this.a1 = a1;
	}

	public ArrayList<Integer> getA2() {
		return a2;
	}

	public void setA2(ArrayList<Integer> a2) {
		this.a2 = a2;
	}
	
	public boolean isShowEncode() {
		return showEncode;
	}

	public void setShowEncode(boolean showEncode) {
		this.showEncode = showEncode;
	}

	public String shuffle() {
		Util.shuffle(a1);
		Util.shuffle(a2);
		showEncode = true;
		return null;
	}

}
