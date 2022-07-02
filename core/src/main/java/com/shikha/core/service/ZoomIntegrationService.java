package com.shikha.core.service;

import org.json.JSONObject;

public interface ZoomIntegrationService {
	
	public String createZoomMeeting();
	public String getJWTtoken();
	public String getZoomUsers();
	public String getInvitation();
	public String listMeetingsByUserId();
	public boolean isZoomUser(String LoggedInMailId);
	

}
