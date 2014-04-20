package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.models.Election;

public class UserElections {
	private ArrayList<Election> els;
	//private String userId;
	
	public UserElections() {
		FacesContext context = FacesContext.getCurrentInstance();
		//Elections elsBean = (Elections) context.getApplication().evaluateExpressionGet(context, "#{elections}", Elections.class);
		//String accessToken = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("accessToken");
		//FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		//User user = fbClient.fetchObject("me", User.class);
		int userId = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		els = ElectionDP.getUserElections(userId);
	}

	public ArrayList<Election> getEls() {
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}
	
}
