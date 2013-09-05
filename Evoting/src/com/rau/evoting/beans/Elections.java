package com.rau.evoting.beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;
import com.rau.evoting.utils.FacebookService;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.User;

/**
 * @author Aram
 *
 */
public class Elections {
	private String accessToken;
	
	private ArrayList<Election> els;
	private Election selected;
		
	public Election getSelected() {
		return selected;
	}

	public void setSelected(Election selected) {
		this.selected = selected;
	}

	public Elections() {
	}
	
	public ArrayList<Election> getEls() {
		els = SqlDataProvider.getInstance().loadOpenElections();
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@PostConstruct
	public void init() {
		if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("accessToken")) {
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String code = req.getParameter("code");
			accessToken = FacebookService.getInstance().getAccessToken(code, "Elections.xhtml");
			FacebookClient fbClient = new DefaultFacebookClient(accessToken);
			User user = fbClient.fetchObject("me", User.class);
			SqlDataProvider.getInstance().insertUser(user.getId());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accessToken", accessToken);
		}
	}
			
	public String election(int id) {
		return "election";
	}
		
}
