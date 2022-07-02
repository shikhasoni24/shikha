package com.shikha.core.service;

import java.util.List;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.ServiceException;

public interface SearchRoom {
	public List<Room> roomSearch(String city) throws ServiceException;

}
