package com.rau.evoting.beans;

import java.beans.Expression;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.naming.Context;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;

public class OpenedElection {
	
	private Election election;
	
	public OpenedElection() {
	}

	public Election getElection() {
		//Map<String,String> params =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}
	
	public String fromElections(int id) {
		election = SqlDataProvider.getInstance().getElection(id);
		/*FacesContext context = FacesContext.getCurrentInstance();
		Application application =  context.getApplication();
		//application.evaluateExpressionGet(context, "#{election.}", expectedType)
		ExpressionFactory exFact =  application.getExpressionFactory();
        MethodExpression me = exFact.createMethodExpression(context.getELContext(),"#{home.print}" , Void.class, new Class[] {});
        me.invoke(context.getELContext(), null);		*/
		return "Election";
	}
}
