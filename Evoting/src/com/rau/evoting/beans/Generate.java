package com.rau.evoting.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.rau.evoting.data.SqlDataProvider;

public class Generate {
	private int trusteeId;
	private int electionId;
	private String name;
	
	public Generate() {
		trusteeId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trusteeId"));
		electionId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("electionId"));
		SqlDataProvider.getInstance().setTrusteeGenerated(electionId, trusteeId);
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
