package com.rava.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rava.data.UserDP;
import com.rava.model.UserGroup;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import com.restfb.types.Group;
import com.restfb.types.User;

@Path("/login")
public class LoginService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("fbtoken") String fbAccessToken) {
		if (fbAccessToken == null || fbAccessToken.isEmpty()) {
			return Response.status(401).entity("Token null or empty").build();
		}

		System.out.println(fbAccessToken);
		
		FacebookClient fbClient = new DefaultFacebookClient(fbAccessToken);
		User user;
		try {
			user = fbClient.fetchObject("me", User.class);
		} catch (FacebookException exception) {
			return Response.status(401)
					.entity("Not valid token " + fbAccessToken).build();
		}

		System.out.println(user.getId() + " / " + user.getEmail());
		int userId = UserDP.insert(user.getId(), user.getEmail());

		Connection<Group> gr = fbClient.fetchConnection("me/groups",
				Group.class);
		List<Group> groups = gr.getData();
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		for (Group g : groups) {
			userGroups.add(new UserGroup(g.getId(), userId, g.getName()));
			System.out.println(g.getName());
		}

		com.rava.model.User userNative = UserDP.getUser(userId);
		return Response.status(200).entity(userNative).build();
	}

	@GET
	@Path("/info")
	public Response addUser(@HeaderParam("user-agent") String userAgent) {

		return Response.status(200)
				.entity("addUser is called, userAgent : " + userAgent).build();

	}

	@GET
	@Path("/getHeaders")
	public Response addUser(@Context HttpHeaders headers) {
		String userAgent = headers.getRequestHeader("user-agent").get(0);

		return Response
				.status(200)
				.entity("addUser is called, userAgent : " + userAgent + "\n"
						+ headers.getRequestHeaders()).build();

	}
}
