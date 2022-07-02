package com.shikha.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.commons.jcr.JcrConstants;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.UserCancelledBookedRoomService;
import com.shikha.core.service.UserRoomService;
import com.shikha.core.service.UserService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Signup Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/signup" })
public class SignUpServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	UserService userService;
	@Reference
	RoomService roomService;
	@Reference
	UserRoomService userRoomService;
	@Reference
	UserCancelledBookedRoomService ucbrs;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String signupUsername = req.getParameter("susername");
			String signupPassword = req.getParameter("spassword");
			String signupmailId = req.getParameter("smailid");

			String message = userService.setUserData(signupUsername, signupPassword,signupmailId);
			resp.getWriter().write(message);
		} catch (ServiceException e) {
			resp.getWriter().write(e.toString());
		}
		// resp.setContentType("text/html");

	}
}
