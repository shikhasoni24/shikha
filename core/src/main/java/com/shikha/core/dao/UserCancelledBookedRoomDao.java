package com.shikha.core.dao;


import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface UserCancelledBookedRoomDao {
	public String cancelBookedRoom  (int userId,int roomNo) throws DAOException, ServiceException;

}
