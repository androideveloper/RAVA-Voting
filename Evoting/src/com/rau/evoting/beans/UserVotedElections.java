package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.models.Election;

public class UserVotedElections {
	
	private ArrayList<Election> els;
	
	public UserVotedElections() {
		int userId = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		els = ElectionDP.getUserVotedElections(userId);
	}

	public ArrayList<Election> getEls() {
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}
	
}
