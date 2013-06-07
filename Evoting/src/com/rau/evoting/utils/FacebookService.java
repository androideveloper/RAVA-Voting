package com.rau.evoting.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class FacebookService {
	private static FacebookService fbService;
	
	private FacebookService() {
		
	}
	
	public static FacebookService getInstance() {
		if(fbService == null) {
			fbService = new FacebookService();
		}
		return fbService;
	}
	
	public String getAccessToken(String code, String page) {
		String accessToken = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://graph.facebook.com/oauth/access_token");

		try {
			String[][] parameters = {
					{ "client_id", "515272745187738" },
					{ "client_secret", "e37f4bd94fc533c364ad291a2ecbba09" },
					{ "redirect_uri",
							"http://localhost:8080/Evoting/" + page },
					{ "code", code } };

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

			for (int i = 0; i < parameters.length; i++) {
				nameValuePairs.add(new BasicNameValuePair(parameters[i][0],
						parameters[i][1]));
			}

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse resp = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp
					.getEntity().getContent()));

			String message = "";
			String lineData;
			while ((lineData = rd.readLine()) != null) {
				message += lineData;
				System.out.println(lineData);
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
				throw new Exception("no params");
			}

			accessToken = token;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
/*	private int retrieveToken(String code) {
		 
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
	   }*/

}
