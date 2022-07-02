package com.shikha.core.dao;


import com.shikha.core.exception.exceptionimpl.DAOException;

public interface ForgotPassword {
	   public String resetPassword(String username,String newpassword) throws DAOException;

}
