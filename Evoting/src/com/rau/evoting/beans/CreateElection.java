package com.rau.evoting.beans;

import java.util.ArrayList;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Candidate;
import com.rau.evoting.models.Election;

public class CreateElection {

	private String name;
	private String description;
	private ArrayList<Candidate> candidates;
	private Candidate candidate;
	private Candidate selected;
	private int elId;
	private boolean showCreate;

	public CreateElection() {
		candidates = new ArrayList<Candidate>();
		candidate = new Candidate();
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Candidate getSelected() {
		return selected;
	}

	public void setSelected(Candidate selected) {
		this.selected = selected;
	}

	public ArrayList<Candidate> getCandidates() {

		if (candidates.size() == 0) {
			showCreate = false;
		} else {
			showCreate = true;
		}
		return candidates;
	}

	public void setCandidates(ArrayList<Candidate> candidates) {
		this.candidates = candidates;
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
	
	public boolean isShowCreate() {
		return showCreate;
	}

	public void setShowCreate(boolean showCreate) {
		this.showCreate = showCreate;
	}

	public String add() {
		candidates.add(candidate);
		candidate = new Candidate();
		System.out.println(name);
		return "candidate";
	}

	public String cancel() {
		System.out.println(description);
		candidate = new Candidate();
		return "candidate";
	}
	
	public String addElection(){
		elId = SqlDataProvider.getInstance().insertElecttion(new Election(0, name, description),"aa");
		return "next";
	}
	
	public String create() {
		SqlDataProvider.getInstance().insertCandidates(candidates);
		//System.out.println(candidates.get(0).getId());
		SqlDataProvider.getInstance().setElectionCandidates(elId, candidates);
		return "create";
	}
	
}
