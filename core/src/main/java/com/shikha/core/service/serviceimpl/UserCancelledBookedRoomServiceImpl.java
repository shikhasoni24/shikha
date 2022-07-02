package com.shikha.core.service.serviceimpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.RoomDao;
import com.shikha.core.dao.UserBookedRooms;
import com.shikha.core.dao.UserCancelledBookedRoomDao;
import com.shikha.core.entity.Room;
import com.shikha.core.entity.UserRoom;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.InValidRoomIdException;
import com.shikha.core.exception.exceptionimpl.RoomBookedByAnotherUserexception;
import com.shikha.core.exception.exceptionimpl.RoomIsNotBookedException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.UserCancelledBookedRoomService;

@Component(service = UserCancelledBookedRoomService.class, immediate = true)
public class UserCancelledBookedRoomServiceImpl implements UserCancelledBookedRoomService {
	
	@Reference
	UserCancelledBookedRoomDao userCancelledBookedRoomDao;
	@Reference
	RoomDao roomDao;
	@Reference
	UserBookedRooms ubr;

	public String cancelBookedRoom(int userId, int roomNo) throws ServiceException {
		String msg = "";
		try {
			List<Room> allRooms = roomDao.getAllRoomsInfo();
			List<UserRoom> userBookedRoom = ubr.getAllRoomsBookedByUser();
			boolean b = false;
			for (UserRoom u : userBookedRoom) {
				if (u.getUser_id() == userId) {
					if (u.getRoom_no() == roomNo) {
						b = true;
						msg = userCancelledBookedRoomDao.cancelBookedRoom(userId, roomNo);
						break;
					}
				}
			}
			boolean bb = false;
			if (!b) {
				for (Room r : allRooms) {
					if (r.getRoomNo() == roomNo) {
						bb = true;
						if (r.getRoomStatus().equalsIgnoreCase("unavailable")) {
							throw new RoomBookedByAnotherUserexception("This room is not registered with your name.");
						} else
							throw new RoomIsNotBookedException("Room is not booked yet");

					}
				}
				if (!bb) {
					throw new InValidRoomIdException("Invalid room Id");
				}

			}
			return msg;

		} catch (DAOException | RoomBookedByAnotherUserexception | RoomIsNotBookedException
				| InValidRoomIdException e) {
			throw new ServiceException(e);
		}

	}

}