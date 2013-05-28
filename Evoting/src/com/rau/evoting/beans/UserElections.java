package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class UserElections {
	private ArrayList<Election> els;
	private String userId;
	
	public UserElections() {
		FacesContext context = FacesContext.getCurrentInstance();
		Elections elsBean = (Elections) context.getApplication().evaluateExpressionGet(context, "#{elections.username}", Elections.class);
		FacebookClient fbClient = new DefaultFacebookClient(elsBean.getAccessToken());
		User user = fbClient.fetchObject("me", User.class);
		userId = user.getId();
	}

	public ArrayList<Election> getEls() {
		els = SqlDataProvider.getInstance().getUserElections(userId);
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}

	
}
