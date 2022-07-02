package com.shikha.core.dao;

import java.util.List;

import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;


public interface UserDao {
	public String setUserInfo(String userName, String password,String emailid) throws DAOException ;
	public List<User> getUserData() throws DAOException;

}
