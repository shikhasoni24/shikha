package com.shikha.core.service;

import java.util.List;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;


public interface UserRoomService {
	public List<Room> getAllRoomsOfCurrentUser(int userId) throws ServiceException  ;

}
