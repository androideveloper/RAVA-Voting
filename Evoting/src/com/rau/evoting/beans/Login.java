package com.rau.evoting.beans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.User;

public class Login {
	private String appId = "515272745187738";
	private String appSecret = "e37f4bd94fc533c364ad291a2ecbba09";
		
	public Login(){
	}
	
	
	public void fbLogin(ActionEvent event) {
		String fUrl = "https://www.facebook.com/dialog/oauth?"
			      + "client_id=" + appId + "&"
			      + "redirect_uri=" + "http://localhost:8080/Evoting/Home.xhtml" +"&"  
			      + "scope=publish_stream,user_groups,status_update&"
			      + "response_type=code";
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		try {
			response.sendRedirect(fUrl);
			System.out.println(fUrl);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error while login");
		}
	}
	
	public void fbLogout(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		//Elections electionsBean = (Elections) context.getApplication().evaluateExpressionGet(context, "#{elections}", Elections.class);
		String accessToken = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("accessToken");
		String fUrl = "https://www.facebook.com/logout.php?next=http://localhost:8080/Evoting/Login.xhtml&access_token=" + accessToken;
		
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		if(request.getSession(false) != null) {
			request.getSession(false).invalidate(); 
		}
		
		try {
			context.getExternalContext().redirect(fUrl);
			System.out.println(fUrl);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
	
	public String logout() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
		return "";
	}
	
}
