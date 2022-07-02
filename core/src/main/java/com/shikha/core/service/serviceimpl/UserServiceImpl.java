package com.shikha.core.service.serviceimpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.UserDao;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.exception.exceptionimpl.UserAlreadyExist;
import com.shikha.core.exception.exceptionimpl.UserNotFoundException;
import com.shikha.core.service.UserService;

@Component(service = UserService.class, immediate = true)
public class UserServiceImpl implements UserService {

	@Reference
	UserDao userDao;

	public String setUserData(String userName, String password, String emailid) throws ServiceException {

		try {
			List<User> userinfo = userDao.getUserData();

			for (User u : userinfo) {
				if (u.getUserName().equalsIgnoreCase(userName)||u.getEmailId().equalsIgnoreCase(emailid)) {

					throw new UserAlreadyExist("User is already Registered...Please enter some other username");
				}
			}

			return userDao.setUserInfo(userName, password, emailid);

		} catch (DAOException | UserAlreadyExist e) {// catching above exception
			throw new ServiceException(e);// throwing to frontend/servlet

		}
	}

	public User getUserInformation(User user) throws ServiceException {
		try {
			List<User> userinfo = userDao.getUserData();
			boolean b = false;
			for (User u : userinfo) {
				if (u.getUserName().equalsIgnoreCase(user.getUserName())
						&& u.getPassword().equalsIgnoreCase(user.getPassword())) {
					b = true;
					return u;
				}
			}

			throw new UserNotFoundException("User is not Registered.....Please Register!!");

		} catch (DAOException | UserNotFoundException e) {
			throw new ServiceException(e);
		}

	}

}
