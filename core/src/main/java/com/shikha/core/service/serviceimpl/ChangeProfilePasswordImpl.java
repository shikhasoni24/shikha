package com.shikha.core.service.serviceimpl;

import java.util.List;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.ForgotPassword;
import com.shikha.core.dao.UserDao;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.OldPasswordWrongException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.ChangeProfilePassword;

@Component(service = ChangeProfilePassword.class, immediate = true)

public class ChangeProfilePasswordImpl implements ChangeProfilePassword{

	@Reference
	UserDao userDao;
	@Reference
	ForgotPassword forgotPassword;
	
		public String changeProfilePassword(String userName, String oldpassword, String newpassword) throws ServiceException {

		try {
			List<User> userinfo = userDao.getUserData();
			//boolean b = false;
		
				for (User u : userinfo) {
					if (u.getUserName().equalsIgnoreCase(userName)&&u.getPassword().equalsIgnoreCase(oldpassword)) {
						//b = true;
						return forgotPassword.resetPassword(userName, newpassword);
					}
				}
			
			throw new OldPasswordWrongException("Entered Old Password is wrong ");

		} catch (DAOException | OldPasswordWrongException e) {//catching above exception
			throw new ServiceException(e);//throwing to frontend/servlet

		}
	}
}
