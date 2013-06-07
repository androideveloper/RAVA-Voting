package com.rau.evoting.beans;

import java.util.ArrayList;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Candidate;
import com.rau.evoting.models.Election;
import com.rau.evoting.utils.Shuffle;

public class Candidates {

	private ArrayList<Candidate> c1;
	private ArrayList<Candidate> c2;
	private String selectedCandidate1;
	private String selectedCandidate2;
	private boolean showShuffle;
	private String codeValue;
	public   int electId;
	public static boolean reload;	

	public String getCodeValue() {
		return codeValue;
	}


	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}


	public String init(int elId){
		electId = elId;
		showShuffle = true;
		codeValue = "encode";
		//c1 = SqlDataProvider.getInstance().getElectionCandidaets(electId);
		//c2 = SqlDataProvider.getInstance().getElectionCandidaets(electId);
		return "election";
	}
	public Candidates(){
		//init();
		//c1 = SqlDataProvider.getInstance().loadCandidates();
		//c2 = SqlDataProvider.getInstance().loadCandidates();
		//System.out.println(electId);
		//c1 = SqlDataProvider.getInstance().getElectionCandidaets(electId);
		//c2 = SqlDataProvider.getInstance().getElectionCandidaets(electId);
	}


	public ArrayList<Candidate> getC1() {
		return c1;
	}

	public void setC1(ArrayList<Candidate> c1) {
		this.c1 = c1;
	}

	public ArrayList<Candidate> getC2() {
		return c2;
	}

	public void setC2(ArrayList<Candidate> c2) {
		this.c2 = c2;
	}
	

	public String getSelectedCandidate1() {
		return selectedCandidate1;
	}


	public void setSelectedCandidate1(String selectedCandidate1) {
		this.selectedCandidate1 = selectedCandidate1;
	}


	public String getSelectedCandidate2() {
		return selectedCandidate2;
	}


	public void setSelectedCandidate2(String selectedCandidate2) {
		this.selectedCandidate2 = selectedCandidate2;
	}
	
	public boolean isShowShuffle() {
		return showShuffle;
	}

	public void setShowShuffle(boolean showShuffle) {
		this.showShuffle = showShuffle;
	}
	
	public String shuffle() {
		//Shuffle.shuffle(c1);
		//Shuffle.shuffle(c2);
		return "Candidates.xhtml";
	}
	

	public String encode()
	{
		showShuffle = false;
		codeValue = "decode";
		return "encode";
	}
	
}

