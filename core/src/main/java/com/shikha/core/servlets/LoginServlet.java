package com.shikha.core.servlets;

import java.io.IOException;

import javax.mail.MessagingException;
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

import com.google.gson.Gson;
import com.shikha.core.entity.LoggedInUser;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.UserRoomService;
import com.shikha.core.service.UserService;
import com.shikha.core.service.WelcomeEmailService;

@Component(immediate=true,service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Login Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/login" })
public class LoginServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	UserService userService;
	@Reference
	UserRoomService userRoomService;
	@Reference
	RoomService roomService;
//	@Reference
//	WelcomeEmailService welcomeEmailService;
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String message = "";
		try {
			String loginUsername = req.getParameter("lusername");
			String loginPassword = req.getParameter("lpassword");
			User user = userService.getUserInformation(new User(loginUsername,loginPassword));
			
			String roomsSelectedByUser = req.getParameter("userselrooms");
			if(!roomsSelectedByUser.isEmpty()){
				
			
			String[] roomsArr=roomsSelectedByUser.split(",");
			
			
			for (int i = 0; i < roomsArr.length; i++) {
				message = roomService.bookRoom(Integer.parseInt(roomsArr[i]),user.getUserId());

			}}
		//	welcomeEmailService.setEmailData(userName); 
			Gson gson=new Gson();
			String s=gson.toJson(user);
			JSONObject js=new JSONObject();
			js.put("user",s);
			js.put("message", message);
			js.put("resultingpage", "http://localhost:4502/content/shikha/welcome/jwmarriotthotel-.html");
			resp.setContentType("application/json");
			
			resp.getWriter().write(js.toString());
			
		} catch (ServiceException | JSONException e) {
			resp.getWriter().write(e.toString());  //this response will go to success method in ajax call
			/*e.printStackTrace();//this will go to error block in ajax call, because it is not a response
			 and it will create confusion in frontend whether it is an ajax error or error in backend*/
		}
		

	}
}
