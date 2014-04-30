package com.rau.evoting.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionVoteDP;
import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;

public class OpenedElection {
	
	private int elId;
	private Election election;
	private boolean creator = true;
	
	public OpenedElection() {
	}

	public Election getElection() {
		election = ElectionDP.getElection(elId);
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}
		
	public int getElId() {
		return elId;
	}

	public void setElId(int elId) {
		this.elId = elId;
	}

	public boolean isCreator() {
		return creator;
	}

	public void setCreator(boolean creator) {
		this.creator = creator;
	}

/*	public void preRender(){
		System.out.println("election id: " + elId);
		election = ElectionDP.getElection(elId);
		int userId = (int) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userId");
		if(election != null && election.getCreatorId() == userId) {
			creator = true;
		}
	}
	*/
	public String closeElection(){
		System.out.println("aaaa");
		elId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elId"));
		System.out.println("el id is " + elId);
		ElectionVoteDP.cutVotes(elId);
		return "";
	}
	
	public String fromElections(int id) {
		election = ElectionDP.getElection(id);
		/*FacesContext context = FacesContext.getCurrentInstance();
		Application application =  context.getApplication();
		//application.evaluateExpressionGet(context, "#{election.}", expectedType)
		ExpressionFactory exFact =  application.getExpressionFactory();
        MethodExpression me = exFact.createMethodExpression(context.getELContext(),"#{home.print}" , Void.class, new Class[] {});
        me.invoke(context.getELContext(), null);		*/
		return "Election";
	}
}
