package com.rau.evoting.beans;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.rau.evoting.data.UserDP;
import com.rau.evoting.data.UserGroupDP;
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
public class Home {
	/*private String accessToken;
	
	public Home() {
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@PostConstruct
	public void init() {
		System.out.println("home init");
		if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("accessToken")) {
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String code = req.getParameter("code");
			accessToken = FacebookService.getInstance().getAccessToken(code, "Elections.xhtml");
			FacebookClient fbClient = new DefaultFacebookClient(accessToken);
			User user = fbClient.fetchObject("me", User.class);
			int userId = SqlDataProvider.getInstance().insertUser(user.getId(), user.getEmail());
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("accessToken", accessToken); 
			Connection<Group> gr = fbClient.fetchConnection("me/groups", Group.class);
			List<Group> groups = gr.getData();
			sessionMap.put("userGroups", groups);  
			SqlDataProvider.getInstance().insertUserGroups(userId, groups); 
			sessionMap.put("userId", userId);
		}
		// load groups not depending on if
	}*/
	
	public void getAccessToken() {
		System.out.println("home init");
		if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("accessToken")) {
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String code = req.getParameter("code");
			String accessToken = FacebookService.getInstance().getAccessToken(code, "Home.xhtml");
			FacebookClient fbClient = new DefaultFacebookClient(accessToken);
			User user = fbClient.fetchObject("me", User.class);
			int userId = UserDP.insert(user.getId(), user.getEmail());
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("accessToken", accessToken); 
			Connection<Group> gr = fbClient.fetchConnection("me/groups", Group.class);
			List<Group> groups = gr.getData();
			sessionMap.put("userGroups", groups);  
			UserGroupDP.insert(userId, groups); 
			sessionMap.put("userId", userId);
			sessionMap.put("username", user.getName());
		}
		return;
	}
	
	public String getUsername() {
		String username = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("username");
		return username;
	}
			
}
