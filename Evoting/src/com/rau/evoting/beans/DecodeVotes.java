package com.rau.evoting.beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.rau.evoting.ElGamal.ElGamalHelper;
import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.data.ElectionVoteDP;
import com.rau.evoting.models.CutVote;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;

public class DecodeVotes {

	private int electId;
	private String privateKey = "";
	private boolean validToken;

	public DecodeVotes() {
		Map<String, String> reqMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		validToken = true;
		if (reqMap.containsKey("trId")) {
			electId = Integer.valueOf(reqMap.get("elId"));
			int trId = Integer.valueOf(reqMap.get("trId"));
			String token = reqMap.get("token");
			Trustee tr = ElectionTrusteeDP.getElectionTrustee(trId);
			if (!tr.getToken().equals(token)) {
				validToken = false;
			} else {
				validToken = true;
			}
		}
	}

	public String decode() {
		electId = Integer.valueOf(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("elId"));
		System.out.println("private key is: " + privateKey);
		System.out.println("election id is: " + electId);
		Election election = ElectionDP.getElection(electId);
		ElGamalHelper gamal = new ElGamalHelper(election.getPublicKey(),
				privateKey);

		ArrayList<CutVote> votes = ElectionVoteDP.getCutVotes(election.getId());
		for (CutVote vote : votes) {
			System.out.println("encoded answer sequence: "
					+ vote.getAnswersSequence());
			vote.setAnswersSequence(gamal.decodeBigInt(vote
					.getAnswersSequence()));
			System.out.println("answer sequence: " + vote.getAnswersSequence());
		}
		ElectionVoteDP.updateCutVotes(votes, election.getId());
		return "ThankYou?faces-redirect=true";
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

	public int getElectId() {
		return electId;
	}

	public void setElectId(int electId) {
		this.electId = electId;
	}
}
