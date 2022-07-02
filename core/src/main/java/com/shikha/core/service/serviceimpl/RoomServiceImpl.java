package com.shikha.core.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.RoomDao;
import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.InValidRoomIdException;
import com.shikha.core.exception.exceptionimpl.NoRoomAvailableException;
import com.shikha.core.exception.exceptionimpl.RoomAlreadyBookedException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;

@Component(service = RoomService.class, immediate = true)
public class RoomServiceImpl implements RoomService {

	@Reference
	RoomDao roomDao;

	@Override
	public List<Room> getAllAvailableRooms() throws ServiceException {
		List<Room> availableRooms = new ArrayList<Room>();
		try {
			List<Room> roomFromDao = roomDao.getAllRoomsInfo();
			for (Room r : roomFromDao) {
				if (r.getRoomStatus().equalsIgnoreCase("available")) {
					availableRooms.add(r);
				}
			}
			if (availableRooms.size() == 0) {
				throw new NoRoomAvailableException("The hotel is completely booked");
			} else
				return availableRooms;

		} catch (DAOException | NoRoomAvailableException e) {
			throw new ServiceException(e);
		}
	}

	public String bookRoom(int roomNo, int userId) throws ServiceException {
		List<Room> roomFromDao;
		String messasge = "";
		boolean statusOfRoom = false;
		boolean availabilityStatus = false;
		try {
			roomFromDao = roomDao.getAllRoomsInfo();
			for (Room r : roomFromDao) {
				if (r.getRoomNo() == roomNo) {
					statusOfRoom = true;
					if (r.getRoomStatus().equalsIgnoreCase("available")) {
						availabilityStatus = true;
						messasge = roomDao.bookAvailableRoom(userId, roomNo);
						break;
					}else
						break;
				}
			}
			if (!statusOfRoom) {
				throw new InValidRoomIdException(" Room no. does not exist");
			} else if (!availabilityStatus) {
				throw new RoomAlreadyBookedException(" This Room is already booked");
			}
			return messasge;
		}

		catch (DAOException | InValidRoomIdException | RoomAlreadyBookedException e) {
			throw new ServiceException(e);
		}
	}
}
