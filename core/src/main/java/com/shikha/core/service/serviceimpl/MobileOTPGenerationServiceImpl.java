package com.shikha.core.service.serviceimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import com.shikha.core.service.MobileOTPGenerationService;

//@Component(service=MobileOTPGenerationService.class,immediate=true)

public class MobileOTPGenerationServiceImpl implements MobileOTPGenerationService {

	public static void main(String[] args) throws IOException {
		String sendMethod = "generate";

		String apiKey = "130491ADE9VcIY7WyB5vst82d3";
		String mobiles = "7979925545";
		Random rand = new Random();
		int otp = rand.nextInt(900000) + 100000;
		String senderId = "SGCSMS";
		// Your message to send, Add URL encoding here.
		String message = "Your OTP is " + otp;
		// define route
		String route = "4";
		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;
		// encoding message
		String encoded_message = URLEncoder.encode(message);
		// Send SMS API
		String mainUrl = "https://www.smsgateway.center/OTPApi/send?";
		// Prepare parameter string
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("apiKey=" + apiKey);
		sbPostData.append("&sendMethod="+sendMethod);
		sbPostData.append("&mobiles=" + mobiles);
		sbPostData.append("&message=" + encoded_message);
		sbPostData.append("&route=" + route);
		sbPostData.append("&sender=" + senderId);
		// final string
		mainUrl = sbPostData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			String success = "Your message sent sucessfully";
			System.out.println(success);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String response;

		while ((response = reader.readLine()) != null)
			//print response
			System.out.println(response);

			//finally close connection
			reader.close();
			
	}}

	// public String mobileOtpgeneration(){
	//
	//
	// return null;
	//
	// }

