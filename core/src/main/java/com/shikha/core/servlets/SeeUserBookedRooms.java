package com.shikha.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.UserRoomService;
import com.shikha.core.service.UserService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=SeeUserBookedRooms Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/seeUserBookedRooms" })
public class SeeUserBookedRooms extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	UserService userService;
	@Reference
	RoomService roomService;
	@Reference
	UserRoomService userRoomService;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String userid = req.getParameter("d");

			List<Room> room = userRoomService.getAllRoomsOfCurrentUser(Integer.parseInt(userid));
			JSONObject j = new JSONObject();
			j.put("room", room);
			resp.setContentType("application/json");
			/*line no 47: writing this because we want to use room as data.room in ajax success method, without this
			it will give undefined*/
			resp.getWriter().write(j.toString());
		} catch (ServiceException | JSONException e) {
			resp.getWriter().write(e.toString());
		}

	}
}
