package com.rava.resources;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class Hello {

	@GET
	@Path("/test")
	@Produces("text/plain")
	public String hello(@Context HttpServletRequest req) {

		HttpSession session = req.getSession(true);
		Object foo = session.getAttribute("foo");
		if (foo != null) {
			System.out.println(foo.toString());
		} else {
			foo = UUID.randomUUID();
			session.setAttribute("foo", foo);
		}
		return foo.toString();

	}

	@POST
	@Path("/{param}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("param") String param) {
		return "Hellooooo " + param;
	}

	@GET
	@Path("/id/{id : \\d+}")
	public Response getById(@PathParam("id") String id) {
		return Response.status(200).entity("getById is called, id " + id)
				.build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHtml() {
		return "<html>" + "<title>" + "Hello world" + "</title>" + "<body>"
				+ "Hello wooooorld" + "</body>" + "</html>";
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String sayHelloXML() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}
}
