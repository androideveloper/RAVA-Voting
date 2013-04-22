package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.*;

public class UserElections {
	private ArrayList<Election> els;
	private String username;
	
	public UserElections() {
		FacesContext context = FacesContext.getCurrentInstance();
		username = (String) context.getApplication().evaluateExpressionGet(context, "#{home.username}", String.class);
	}

	public ArrayList<Election> getEls() {
		els = SqlDataProvider.getInstance().getUserElections(username);
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}

	
}
