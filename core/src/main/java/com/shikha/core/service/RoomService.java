package com.shikha.core.service;

import java.util.List;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;


public interface RoomService {
  public List<Room> getAllAvailableRooms() throws ServiceException;
	public String bookRoom(int roomNo,int userId) throws ServiceException;
}
