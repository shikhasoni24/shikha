package com.shikha.core.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.service.ZoomIntegrationService;

@Component(service = Servlet.class, immediate = true, property = {
		"sling.servlet.paths=/bin/data/ourzoommeeting","sling.servlet.methods={GET,POST}"})
public class ZoomMeetingServlet extends SlingAllMethodsServlet {

	@Reference
	ZoomIntegrationService zoomIntegrationService;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String loggedMailId=req.getParameter("mailid");
		
		boolean b=zoomIntegrationService.isZoomUser(loggedMailId);
		String invite = zoomIntegrationService.getInvitation();
		String listMeetings=zoomIntegrationService.listMeetingsByUserId();
		JSONObject obj = new JSONObject();

		try {
			
			obj.put("invite", invite);
			obj.put("listMeetings",listMeetings);
		obj.put("isZoomUser", b);
		String a=zoomIntegrationService.createZoomMeeting();
		
			resp.getWriter().write(obj.toString()+a);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.getWriter().write("hello dopost");
		String s=zoomIntegrationService.createZoomMeeting();
		resp.setContentType("application/json");
		resp.getWriter().write(s);
		
	}
}
