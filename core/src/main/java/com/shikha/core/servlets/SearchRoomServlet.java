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

import com.shikha.core.entity.LoggedInUser;
import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.SearchRoom;
import com.shikha.core.service.UserCancelledBookedRoomService;
import com.shikha.core.service.UserRoomService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=SearchRoom Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/searchRoom" })
public class SearchRoomServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	RoomService roomService;
	@Reference
	UserRoomService userRoomService;
	@Reference
	SearchRoom searchRoom;
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			
			String city = req.getParameter("city");
				List<Room> availableRooms=searchRoom.roomSearch(city);
				JSONObject json=new JSONObject();
				json.put("availableRooms", availableRooms);
				resp.setContentType("application/json");
				resp.getWriter().write(json.toString());
		
		} catch (ServiceException | JSONException e) {
			resp.getWriter().write(e.toString());
		}

	}
}
