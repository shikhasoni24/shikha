package com.shikha.core.service.serviceimpl;

import java.util.Random;

import org.osgi.service.component.annotations.Component;
import com.shikha.core.service.OTPGenerationService;

@Component(service = OTPGenerationService.class, immediate = true)
public class OTPGenerationServiceImpl implements OTPGenerationService {
	
	
	public String generateOtp() {
		String number="0123456789";
		Random random = new Random();
		String otp="";
		for(int i=0;i<4;i++){
			otp+=number.charAt(random.nextInt(number.length()));
		}
				
		return otp;
		
	}

}
