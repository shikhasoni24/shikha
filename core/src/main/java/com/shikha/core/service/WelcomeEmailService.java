package com.shikha.core.service;

import java.io.ByteArrayOutputStream;

import javax.mail.MessagingException;

import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface WelcomeEmailService  {
	public void setEmailData(String loginUsername,	ByteArrayOutputStream baos , String fileFormat) throws ServiceException, MessagingException;
	public void sendOTP(String emailid, String otp) throws ServiceException;


}
