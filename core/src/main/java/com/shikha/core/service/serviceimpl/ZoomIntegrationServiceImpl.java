package com.shikha.core.service.serviceimpl;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shikha.core.service.ZoomIntegrationService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component(service = ZoomIntegrationService.class, immediate = true)
public class ZoomIntegrationServiceImpl implements ZoomIntegrationService {

	@Override
	public String getJWTtoken() {/*
									 * we need this authentication token to hit
									 * the zoom API each time we want to do any
									 * CRUD operation this is created by zoom
									 * marketplace(it will expire after given
									 * time) also but we will create it through
									 * code each time to avoid its expiry.we
									 * need following variables to create JWT
									 * token and each time code runs it will be
									 * generate unique JWT token apikey,
									 * apisecret key, signingKey, eTime.
									 */
		int eTime = 90;
		String apiKey = "fjsk7yPyTT6HqWrEgvDPKA";
		String apiSecret = "3e9nvGzQ7fmOloT384mHHDIQozVLegwPm76a";
		byte[] apiKeySecretBytes = apiSecret.getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

		// set headers
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");

		// setexpiration to 90min
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, eTime / 60);
		now.add(Calendar.MINUTE, eTime % 60);
		Date expirationDate = now.getTime();

		// Generating JWT token
		JwtBuilder builder = Jwts.builder();
		builder.setHeader(header);
		builder.setIssuer(apiKey);
		builder.setExpiration(expirationDate);
		builder.signWith(SignatureAlgorithm.HS256, signingKey);
		return builder.compact();
	}

	@Override
	public String getZoomUsers() { /*
									 * to get all the zoom users of particular
									 * zoom account Note: 2 types of account can
									 * be created in zoom 1)Account based 2)User
									 * Based -- we are dealing with the account
									 * based
									 */
		URL url;
		// JSONObject obj = null;
		String str = null;
		try {
			url = new URL("https://api.zoom.us/v2/users/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			 connection.setRequestProperty("Accept", "application/json");
			 connection.setRequestProperty("content-type","application/json");
			connection.setRequestProperty("Authorization", "Bearer " + getJWTtoken());
			InputStream responseStream = connection.getInputStream();
			InputStreamReader input=new InputStreamReader(responseStream);
			BufferedReader reader = new BufferedReader(input);
			StringBuilder stringBuilder=new StringBuilder();
			String content="";
			while((content=reader.readLine())!=null){
				stringBuilder.append(content);
			}
			str=stringBuilder.toString();
			
			
			/*note: at first we used object mapper to convert inputstream to map thinking we will thn use str = jsonMap.toString();
			 * to covert it to string but output was below
		output:	 {page_count=1, page_number=1, page_size=30, total_records=1, next_page_token=, users=[{id=9NRNQF9rStClBiCicIjTxw, first_name=Shikha, last_name=Soni, email=shikhasoni1224@gmail.com, type=1, pmi=2081757337, timezone=Asia/Calcutta, verified=1, created_at=2021-09-19T07:14:12Z, last_login_time=2021-09-24T05:55:30Z, last_client_version=5.7.7.2047(android), language=en-US, phone_number=, status=active, role_id=0}]}
		ouput was coming in above way so we were not able to convert to json object using
		JSONObject json=new JSONObject(str);
		
		we wanted output in below way so thats why we used InputStreamReader or IOUtil.
		{"page_count":1,"page_number":1,"page_size":30,"total_records":1,"next_page_token":","users":[{"id":"9NRNQF9rStClBiCicIjTxw","first_name":"Shikha","last_name":"Soni","email":"shikhasoni1224@gmail.com","type":1,"pmi":2081757337,"timezone":"Asia/Calcutta","verified":1,"created_at":"2021-09-19T07:14:12Z","last_login_time":"2021-09-24T05:55:30Z","last_client_version":"5.7.7.2047(android)","language":"en-US","phone_number":"","status":"active","role_id":"0"}]}
			 	*/
			
//			ObjectMapper mapper = new ObjectMapper();
//			Map<String, Object> jsonMap = mapper.readValue(responseStream, Map.class);
//			str = jsonMap.toString();
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public boolean isZoomUser(String LoggedInMailId) {
		String zoomUsers = getZoomUsers();
		boolean isZoom = false;
		try {
			JSONObject obj = new JSONObject(zoomUsers);
			JSONArray jsonArr = obj.getJSONArray("users");
			for (int i = 0; i < jsonArr.length(); i++) {

				JSONObject user = jsonArr.getJSONObject(i);
				if (user.has("email")) {
					if (user.get("email").toString().equalsIgnoreCase(LoggedInMailId)) {
						isZoom = true;
						break;
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isZoom;
	}

	@Override
	public String listMeetingsByUserId() {
		URL url;
		// JSONObject obj = null;
		String str = null;
		try {
			url = new URL("https://api.zoom.us/v2/users/9NRNQF9rStClBiCicIjTxw/meetings");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// connection.setRequestProperty("Accept", "application/json");
			// connection.setRequestProperty("content-type","application/json");
			connection.setRequestProperty("Authorization", "Bearer " + getJWTtoken());
			InputStream responseStream = connection.getInputStream();
			
			if (responseStream != null) {
				str = IOUtils.toString(responseStream, "UTF-8");					
			}
			//changing any input stream to string in above  line
			JSONObject obj=new JSONObject(str);
			// connection.getResponseCode();
//			ObjectMapper mapper = new ObjectMapper();
//			Map<String, Object> jsonMap = mapper.readValue(responseStream, Map.class);
//			str = jsonMap.toString();
			// obj=new JSONObject(str);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
}

	@Override
	public String getInvitation() {/*
									 * to get zoom invitation link of the
									 * particular meeting id received from the
									 * above method
									 */
		URL url;
		// JSONObject obj = null;
		String str = null;
		try {
			url = new URL("https://api.zoom.us/v2/meetings/82777883578/invitation");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// connection.setRequestProperty("Accept", "application/json");
			// connection.setRequestProperty("content-type","application/json");
			connection.setRequestProperty("Authorization", "Bearer " + getJWTtoken());
			InputStream responseStream = connection.getInputStream();
			// connection.getResponseCode();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> jsonMap = mapper.readValue(responseStream, Map.class);
			str = jsonMap.toString();
			// obj=new JSONObject(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String createZoomMeeting() {
		URL url;
		// JSONObject obj = null;
		String str = null;
		try {
			url = new URL("https://api.zoom.us/v2/users/9NRNQF9rStClBiCicIjTxw/meetings");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			 connection.setRequestProperty("Accept", "application/json");
			 connection.setRequestProperty("content-type","application/json");
			connection.setRequestProperty("Authorization", "Bearer " + getJWTtoken());
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("topic","aem discussion");
			jsonObj.put("type",2);
			jsonObj.put("start_time","2021-09-25T12:10:10Z");
			jsonObj.put("duration",3);
			JSONObject setting=new JSONObject();
			
			setting.put("host_video",true);
				setting.put("participant_video",true);
				setting.put( "join_before_host",true);
				setting.put( "mute_upon_entry",true);
				setting.put("watermark",true);
				setting.put("audio","voip");
				setting.put("auto_recording","cloud");
				jsonObj.put("settings",setting);
				connection.setDoOutput(true);
				String jsonString = jsonObj.toString();
				try(OutputStream os = connection.getOutputStream()) {
				    byte[] input = jsonString.getBytes("utf-8");
				    os.write(input, 0, input.length);			
				}
			
			InputStream responseStream = connection.getInputStream();
			InputStreamReader input=new InputStreamReader(responseStream);
			BufferedReader reader = new BufferedReader(input);
			StringBuilder stringBuilder=new StringBuilder();
			String content="";
			while((content=reader.readLine())!=null){
				
				stringBuilder.append(content);
			}
			str=stringBuilder.toString();
//			 ObjectMapper mapper = new ObjectMapper();
//			Map<String, Object> jsonMap = mapper.readValue(responseStream, Map.class);
//			str = jsonMap.toString();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;

	}

}
