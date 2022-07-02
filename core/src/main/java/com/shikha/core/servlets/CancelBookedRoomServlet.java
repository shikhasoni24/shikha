package com.shikha.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.entity.LoggedInUser;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.UserCancelledBookedRoomService;
import com.shikha.core.service.UserRoomService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Cancel room Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/cancelroom" })
public class CancelBookedRoomServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	RoomService roomService;
	@Reference
	UserRoomService userRoomService;
	@Reference
	UserCancelledBookedRoomService ucbrs;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String message = "";
		try {
			String[] arra = req.getParameterValues("selectedrooms");
			/*getParameterValues --> when we are receiving array from ajax call,
			it works like selectedrooms or selectedrooms[](not sure selectedrooms[], did not work for us)*/

			String loggedInUserId = req.getParameter("loggedInUserId");

			for (int i = 0; i < arra.length; i++) {
				message = ucbrs.cancelBookedRoom(Integer.parseInt(loggedInUserId),Integer.parseInt(arra[i]));

			}
			resp.getWriter().write(message);
		} catch (ServiceException e) {
			resp.getWriter().write(e.toString());
		}
		// resp.setContentType("text/html");

	}
}
