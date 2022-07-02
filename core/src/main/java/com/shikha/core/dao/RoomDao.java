package com.shikha.core.dao;

import java.util.List;

import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.DAOException;

public interface RoomDao {
   public List<Room> getAllRoomsInfo() throws DAOException;
   public String bookAvailableRoom  (int userId,int roomNo) throws DAOException;
}
