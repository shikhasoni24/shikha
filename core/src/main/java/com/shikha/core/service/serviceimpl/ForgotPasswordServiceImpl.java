package com.shikha.core.service.serviceimpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.ForgotPassword;
import com.shikha.core.dao.UserDao;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.exception.exceptionimpl.UserNotFoundException;
import com.shikha.core.service.ForgotPasswordService;

@Component(service = ForgotPasswordService.class, immediate = true)
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Reference
	ForgotPassword forgotPassword;
	@Reference
	UserDao userDao;

	public String resetThePassword(String loginUsername, String newPassword) throws ServiceException {
		try {
			List<User> userinfo = userDao.getUserData();
			boolean b = false;
			for (User u : userinfo) {
				if (u.getUserName().equalsIgnoreCase(loginUsername)) {
						b = true;
					return forgotPassword.resetPassword(loginUsername, newPassword);

				}
			}
			throw new UserNotFoundException("User is not Registered.....Please Register!!");
			
		} catch (DAOException | UserNotFoundException e) {
			throw new ServiceException(e);

		}

	}
}
