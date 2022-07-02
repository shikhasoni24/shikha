package com.shikha.core.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.RoomDao;
import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.NoRoomAvailableException;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.RoomService;
import com.shikha.core.service.SearchRoom;

@Component(service = SearchRoom.class, immediate = true)
public class SearchRoomImpl implements SearchRoom{
	
	@Reference
	RoomDao roomDao;
	
	
	public List<Room> roomSearch(String city) throws ServiceException{

		List<Room> availableRooms = new ArrayList<Room>();
		try {
			List<Room> roomFromDao = roomDao.getAllRoomsInfo();
			for (Room r : roomFromDao) {
				if (r.getCity().equalsIgnoreCase(city)) {
					availableRooms.add(r);
				}
			}
			if (availableRooms.size() == 0) {
				throw new NoRoomAvailableException("No Room is available in this city");
			} else
				return availableRooms;

		} catch (DAOException | NoRoomAvailableException e) {
			throw new ServiceException(e);
		}
	
		
	}

}
