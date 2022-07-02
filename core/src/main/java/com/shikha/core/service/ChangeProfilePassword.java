package com.shikha.core.service;

import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface ChangeProfilePassword {
	public String changeProfilePassword(String userName, String oldpassword, String newpassword) throws ServiceException;

}
