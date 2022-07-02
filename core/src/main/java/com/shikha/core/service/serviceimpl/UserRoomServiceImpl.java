package com.shikha.core.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.RoomDao;
import com.shikha.core.dao.UserBookedRooms;
import com.shikha.core.entity.Room;
import com.shikha.core.entity.UserRoom;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.NoRoomBookedAtYourName;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.UserRoomService;

@Component(service = UserRoomService.class, immediate = true)
public class UserRoomServiceImpl implements UserRoomService {

	@Reference
	RoomDao roomDao;
	@Reference
	UserBookedRooms ubr;

	public List<Room> getAllRoomsOfCurrentUser(int userId) throws ServiceException  {
		List<Room> bookedRooms = new ArrayList<>();
		try {
			List<Room> allRooms = roomDao.getAllRoomsInfo();
			List<UserRoom> userBookedRoom = ubr.getAllRoomsBookedByUser();
			for (UserRoom u : userBookedRoom) {
				if (u.getUser_id() == userId) {
					for (Room room : allRooms) {
						if(room.getRoomNo()==u.getRoom_no()){
							bookedRooms.add(room);
							break;
						}
					}					
				}
			}
			if(bookedRooms.size()==0){
				throw new NoRoomBookedAtYourName("You Haven't Booked Any Room. Please Book...");
			}
			return bookedRooms;
			
		} catch (DAOException | NoRoomBookedAtYourName e) {
			throw new ServiceException(e);
			
		}
	}

}
