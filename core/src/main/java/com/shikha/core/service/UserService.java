package com.shikha.core.service;

import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface UserService {
	public String setUserData(String userName, String password, String emailid) throws ServiceException ;
	public User getUserInformation(User user)  throws ServiceException;


}
