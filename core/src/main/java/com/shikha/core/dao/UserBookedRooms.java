package com.shikha.core.dao;

import java.util.List;

import com.shikha.core.entity.UserRoom;
import com.shikha.core.exception.exceptionimpl.DAOException;


public interface UserBookedRooms {
	public List<UserRoom> getAllRoomsBookedByUser() throws DAOException;

}
