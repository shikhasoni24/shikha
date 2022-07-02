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
import com.shikha.core.service.OTPGenerationService;
import com.shikha.core.service.UserService;
import com.shikha.core.service.WelcomeEmailService;

@Component(immediate=true,service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Match OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/matchotp" })
public class MatchOtpServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;

	@Reference
	UserService userService;
	
	@Reference
	OTPGenerationService oTPGenerationService;
	
	@Reference
	WelcomeEmailService welcomeEmailService;
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try {
			String enteredid = req.getParameter("loginuserid");
			String enteredpass = req.getParameter("loginpassword");
			User user = userService.getUserInformation(new User(enteredid,enteredpass));		
			
			String otp=oTPGenerationService.generateOtp();
		//	welcomeEmailService.sendOTP(user.getEmailId(),otp);
		
			JSONObject js=new JSONObject();
			js.put("otp",otp);
			
			resp.setContentType("application/json");
			
			resp.getWriter().write(js.toString()); //this response will go to success method in ajax call
		} catch (ServiceException | JSONException e) {
			resp.getWriter().write(e.toString());  //this response will go to success method in ajax call
			/*e.printStackTrace();//this will go to error block in ajax call, because it is not a response
			 and it will create confusion in frontend whether it is an ajax error or error in backend*/
		}
		

	}
}
