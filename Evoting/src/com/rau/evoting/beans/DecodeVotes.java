package com.rau.evoting.beans;

import java.util.Map;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;

public class DecodeVotes {

	private String privateKey;
	private Election election;
	private boolean validToken;
	
	public DecodeVotes() {
		Map<String, String> reqMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		validToken = false;
		if(reqMap.containsKey("elId")){
			int elId = Integer.valueOf(reqMap.get("elId"));
			int trId = Integer.valueOf(reqMap.get("trId"));
			String token = reqMap.get("token");
			Trustee tr = ElectionTrusteeDP.getElectionTrustee(trId);
			if(tr.getToken().equals(token)) {
				validToken = true;
			}
			election = ElectionDP.getElection(elId);
		}
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public boolean isValidToken() {
		return validToken;
	}

	public void setValidToken(boolean validToken) {
		this.validToken = validToken;
	}
		
}
