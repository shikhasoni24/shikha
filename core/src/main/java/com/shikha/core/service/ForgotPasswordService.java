package com.shikha.core.service;

import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface ForgotPasswordService {
	public String resetThePassword(String loginUsername, String newPassword) throws ServiceException;

}
