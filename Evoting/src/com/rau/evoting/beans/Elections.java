package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;

public class Elections {

	private ArrayList<Election> els;
	private Election selected;
		
	public Election getSelected() {
		return selected;
	}

	public void setSelected(Election selected) {
		this.selected = selected;
	}

	public Elections() {
		//els = SqlDataProvider.getInstance().loadElections();
	}
	
	public ArrayList<Election> getEls() {
		els = SqlDataProvider.getInstance().loadOpenElections();
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}

	public String election(int id) {
//		Candidates.electId = id;
//		FacesContext context = FacesContext.getCurrentInstance();
//		Application application = context.getApplication();
//		MethodBinding methodBinding = application.createMethodBinding(
//				"#{candidates.init}", null);   
//		if (methodBinding != null) {
//			try {
//				methodBinding.invoke(context, null);
//			} catch (Throwable e) {
//			}
//		}
		return "election";
	}
	

	
}
