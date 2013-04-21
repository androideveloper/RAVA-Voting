package com.rau.evoting.beans;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.User;

public class Home {
	private String username;
	private String password;
	
	public Home(){
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login(){
		return "login";
//		SqlDataProvider dataprovider = SqlDataProvider.getInstance(); 
//		User user = dataprovider.getUser(username);
//		if(user != null){
//			if(user.getPassword().equals(password)){;
//				return "create";
//			}
//		}
//		return "error";
	}
}
