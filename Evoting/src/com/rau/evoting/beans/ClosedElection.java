package com.rau.evoting.beans;

import java.util.ArrayList;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;

public class ClosedElection {
	
	private int elId;
	private Election election;
	private ArrayList<Trustee> trustees;
	
	public ClosedElection() {
		
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

	public ArrayList<Trustee> getTrustees() {
		trustees = ElectionTrusteeDP.getElectionTrustees(elId);
		return trustees;
	}

	public void setTrustees(ArrayList<Trustee> trustees) {
		this.trustees = trustees;
	}	
}
