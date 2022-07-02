package com.shikha.core.servlets;

import java.io.IOException;



import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import com.shikha.core.dao.ForgotPassword;
import com.shikha.core.entity.LoggedInUser;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.ChangeProfilePassword;
import com.shikha.core.service.ForgotPasswordService;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.UserCancelledBookedRoomService;
import com.shikha.core.service.UserRoomService;
import com.shikha.core.service.UserService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Change Password Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/changepassword" })
public class ChangePasswordServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;


	@Reference
	ChangeProfilePassword changeProfilePassword;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String username = req.getParameter("unv");
			String oldPassword = req.getParameter("eop");
			String newPassword = req.getParameter("enp");

			String string = changeProfilePassword.changeProfilePassword(username, oldPassword, newPassword);
				resp.getWriter().write(string); 
		} catch (ServiceException e) {
			     resp.getWriter().write(e.toString());  
		}
		

	}
}
