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
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 * @author Aram
 *
 */
public class Elections {
	private String accessToken;

	@ManagedProperty(value="#{home}")
	private Home homeBean;
	
	private ArrayList<Election> els;
	private Election selected;
	private String username;
		
	public Election getSelected() {
		return selected;
	}

	public void setSelected(Election selected) {
		this.selected = selected;
	}

	public Elections() {
		//els = SqlDataProvider.getInstance().loadElections();
	}
	
	public ArrayList<Election> getEls() {
		els = SqlDataProvider.getInstance().loadOpenElections();
		return els;
	}

	public void setEls(ArrayList<Election> els) {
		this.els = els;
	}

	public Home getHomeBean() {
		return homeBean;
	}

	public void setHomeBean(Home homeBean) {
		this.homeBean = homeBean;
	}

	
	public String getUsername() {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User user = fbClient.fetchObject("me", User.class);
		username = user.getUsername();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String code = req.getParameter("code");
		int ret = retrieveToken(code);
	}
	
	private int retrieveToken(String code) {
		 
	       HttpClient client = new DefaultHttpClient();
	       HttpPost post = new HttpPost("https://graph.facebook.com/oauth/access_token");
	 
	       try {
	           String[][] parameters = {
	               {"client_id", "515272745187738"},
	               {"client_secret", "e37f4bd94fc533c364ad291a2ecbba09"},
	               {"redirect_uri",
	                     "http://localhost:8080/Evoting/Elections.xhtml"},
	               {"code", code}
	           };
	 
	           List<NameValuePair> nameValuePairs =  new ArrayList<NameValuePair>(1);
	 
	           for (int i = 0; i < parameters.length; i++) {
	               nameValuePairs.add
	                 (new BasicNameValuePair(parameters[i][0],
	                         parameters[i][1]));
	           }
	 
	           post.setEntity(
	              new UrlEncodedFormEntity(nameValuePairs));
	 
	           HttpResponse resp = client.execute(post);
	           BufferedReader rd =
	                   new BufferedReader(new InputStreamReader(
	                   resp.getEntity().getContent()));
	 
	           String message = "";
	           String lineData;
	           while ((lineData = rd.readLine()) != null) {
	               message += lineData; System.out.println(lineData);
	           }
	           
	           String token = null;
	 
	           // Add more safety traps
	           String[] params = message.split("&");
	           if (params != null) {
	               for (int i = 0; i < params.length; i++) {
	                   if (params[i].contains("access_token")) {
	                       String[] B = params[i].split("=");
	                       if (B != null) {
	                           token = B[1];
	                       }
	                       break;
	                   }
	               }
	           } else {
	               return 0;
	           }
	 
	           accessToken = token;
	           return 1;
	 
	       } catch (Exception e) {
	           return 0;
	       }	 
	   }
		
	public String election(int id) {
//		Candidates.electId = id;
//		FacesContext context = FacesContext.getCurrentInstance();
//		Application application = context.getApplication();
//		MethodBinding methodBinding = application.createMethodBinding(
//				"#{candidates.init}", null);   
//		if (methodBinding != null) {
//			try {
//				methodBinding.invoke(context, null);
//			} catch (Throwable e) {
//			}
//		}
		return "election";
	}
	
	public String aaa() {
		FacesContext context = FacesContext.getCurrentInstance();
		homeBean = (Home) context.getApplication().evaluateExpressionGet(context, "#{home}", Home.class);
		System.out.println(homeBean.getUsername());
		return "a";
	}
	
}
