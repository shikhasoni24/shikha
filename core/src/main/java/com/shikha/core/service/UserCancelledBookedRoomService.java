package com.shikha.core.service;

import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface UserCancelledBookedRoomService {
	public String cancelBookedRoom(int roomNo,int userId) throws ServiceException ;

}
