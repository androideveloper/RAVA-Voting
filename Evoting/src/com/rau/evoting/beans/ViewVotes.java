package com.rau.evoting.beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.data.ElectionVoteDP;
import com.rau.evoting.models.CutVote;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;
import com.rau.evoting.utils.StringHelper;

public class ViewVotes {
	
	private Election election;
	private boolean showVotes;
	private ArrayList<String> votes;
	
	public ViewVotes() {
		showVotes = false;
		Map<String, String> reqMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		if (reqMap.containsKey("elId")) { 
			int electId = Integer.valueOf(reqMap.get("elId"));
			election = ElectionDP.getElection(electId);
		}
	}
	
	public String showVotes() {
		//int elId = Integer.valueOf(FacesContext.getCurrentInstance()
		//		.getExternalContext().getRequestParameterMap().get("elId"));
		//election = ElectionDP.getElection(elId);
		int stage = Integer.valueOf(FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap().get("stage"));
		ArrayList<CutVote> v = ElectionVoteDP.getCutVotes(election.getId(), stage);
		votes = new ArrayList<String>();
		for(CutVote vote : v) {
			votes.add(StringHelper.getSHA256hash(vote.getAnswersSequence()));
		}
		showVotes = true;
		return "";
	}

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}

	public ArrayList<String> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<String> votes) {
		this.votes = votes;
	}
}
