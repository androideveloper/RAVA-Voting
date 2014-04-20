package com.rau.evoting.beans;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.models.Election;

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
		//els = SqlDataProvider.getInstance().loadOpenElections(); 
		int userId = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		els = ElectionDP.loadOpenElectionsforUser(userId);
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

/*	@PostConstruct
	public void init() {
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
			
	public String election(int id) {
		return "election";
	}
		
}
