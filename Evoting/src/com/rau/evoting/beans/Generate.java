package com.rau.evoting.beans;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionTrusteeDP;

public class Generate {
	private int trusteeId;
	private int electionId;
	private String name;
	
	public Generate() {
		trusteeId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trusteeId"));
		electionId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("electionId"));
		ElectionTrusteeDP.setTrusteeGenerated(electionId, trusteeId);
	}

	
	public int getTrusteeId() {
		return trusteeId;
	}

	public void setTrusteeId(int trusteeId) {
		this.trusteeId = trusteeId;
	}

	public int getElectionId() {
		return electionId;
	}

	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
