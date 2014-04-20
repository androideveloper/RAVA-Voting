package com.rau.evoting.beans;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rau.evoting.data.ElectionTrusteeDP;
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
		Map<String,String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(reqMap.containsKey("trId")) {
			tempTrId = Integer.valueOf(reqMap.get("trId"));
			token = reqMap.get("token");
			Trustee tr = ElectionTrusteeDP.getElectionTrustee(tempTrId);
			System.out.println(token + " ... " + tr.getToken());
			if(!tr.getToken().equals(token)) {
				error = true;
			}
			else {
				error = false;
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("trId", tempTrId);
			}
		}
	}
	
	public void fbLogin(ActionEvent event) {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
		String fUrl = "https://www.facebook.com/dialog/oauth?"
			      + "client_id=" + appId + "&"
			      + "redirect_uri=" + "http://localhost:8080/Evoting/TrusteeElection.xhtml"+"&"  
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
