package com.rau.evoting.beans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Trustee;

public class TrusteeHomeBean {
	private String appId = "515272745187738";
	private String appSecret = "e37f4bd94fc533c364ad291a2ecbba09";
	
	private int tempTrId;
	private String token;
	private int electionId;
	private String login = "Please login for your trustee dashboard";
	private boolean error;
	
	public TrusteeHomeBean() {
		tempTrId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trId"));
		token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");
		Trustee tr = SqlDataProvider.getInstance().getElectionTrustee(tempTrId);
		if(!tr.getToken().equals(token)) {
			error = true;
		}
		else {
			error = false;
		}
	}
	
	public void empty() {
		
	}
	public void fbLogin(ActionEvent event) {
		String fUrl = "https://www.facebook.com/dialog/oauth?"
			      + "client_id=" + appId + "&"
			      + "redirect_uri=" + "http://localhost:8080/Evoting/TrusteeElection.xhtml" +"&"  
			      + "scope=publish_stream,user_groups,status_update,email&"
			      + "response_type=code";
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		try {
			response.sendRedirect(fUrl);
			System.out.println(fUrl);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}

	public int getTempTrId() {
		return tempTrId;
	}

	public void setTempTrId(int tempTrId) {
		this.tempTrId = tempTrId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

		
}
