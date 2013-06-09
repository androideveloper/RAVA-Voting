package com.rau.evoting.beans;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;
import com.rau.evoting.utils.FacebookService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class TrusteeElectionBean {
	private String accessToken;
	
	private int tempTrId;
	private Election election;
	
	public TrusteeElectionBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		tempTrId = (int)context.getApplication().evaluateExpressionGet(context, "#{trhome.tempTrId}", int.class);
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String code = req.getParameter("code");
		accessToken = FacebookService.getInstance().getAccessToken(code, "TrusteeElection.xhtml");
		//int elId = SqlDataProvider.getInstance().getTrusteeElectionId(tempTrId);
		//election = SqlDataProvider.getInstance().getElection(elId);
		election = SqlDataProvider.getInstance().getTrusteeElection(tempTrId);
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User user = fbClient.fetchObject("me", User.class);
		Trustee tr = new Trustee(user.getId(), user.getEmail(), false);
		SqlDataProvider.getInstance().updateTrustee(tempTrId, tr);
		//SqlDataProvider.getInstance().insertTrustee(election.getId(), tr);
		//SqlDataProvider.getInstance().deleteTempTrustee(tempTrId);
	}

	public int getTempTrId() {
		return tempTrId;
	}

	public void setTempTrId(int tempTrId) {
		this.tempTrId = tempTrId;
	}

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}
		
}
