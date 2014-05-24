package com.rau.evoting.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.mail.MessagingException;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.data.ElectionVoteDP;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;
import com.rau.evoting.utils.MailService;
import com.rau.evoting.utils.Util;

public class OpenedElection {
	
	private int elId;
	private Election election;
	private ArrayList<Trustee> trustees;
	
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

	public ArrayList<Trustee> getTrustees() {
		trustees = ElectionTrusteeDP.getElectionTrustees(elId);
		return trustees;
	}

	public void setTrustees(ArrayList<Trustee> trustees) {
		this.trustees = trustees;
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
		elId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elId"));
		System.out.println("el id is " + elId);
		ElectionVoteDP.cutVotes(elId);
		election = ElectionDP.getElection(elId);
		//send mail to all trustees make async!!!!!
		//List<Trustee> trustees = ElectionTrusteeDP.getElectionTrustees(elId);
		//String message = "Please follow this link to upload your private key and decode election votes: \n";
		String title = "Trustee for " + election.getName() + " election";
		//String url = "http://localhost:8080/Evoting/DecodeVotes.xhtml?elId=" + elId;
		/*for(Trustee tr : trustees) {
			url += "&trId=" + tr.getId() + "&token=" + tr.getToken();
			message += url;
			try {
				MailService.sendMessage(tr.getEmail(), title, message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}*/
		Trustee trustee = ElectionTrusteeDP.getTrusteeByMixServer(elId, 1);
		String message = "Please follow this link to open your mix node: \n";
		String url ="http://localhost:8080/Evoting/MixNode.xhtml?trId=" + trustee.getId() + "&token=" + trustee.getToken();
		message += url;
		try {
			MailService.sendMessage(trustee.getEmail(), title, message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Home?faces-redirect=true";
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
