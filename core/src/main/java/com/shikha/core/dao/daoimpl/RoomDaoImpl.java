package com.shikha.core.dao.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.RoomDao;
import com.shikha.core.entity.Room;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.DatabaseException;
import com.shikha.core.utility.JDBCconnection;

@Component(service = RoomDao.class, immediate = true)
public class RoomDaoImpl implements RoomDao {

	@Reference
	JDBCconnection jdbc;

	@Override
	public List<Room> getAllRoomsInfo() throws DAOException {
		List<Room> rooms = new ArrayList<Room>();
		String query = "select * from roominfo";

		Connection con = null;
		try {
			con = jdbc.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				int roomNo = res.getInt(1);
				String roomName = res.getString(2);
				String roomStatus = res.getString(3);
				String city=res.getString(4);
				rooms.add(new Room(roomNo, roomName, roomStatus,city));
				
			}
			return rooms;
		} catch (DatabaseException | SQLException e) {
			throw new DAOException(e);
		}
		finally{
				try {
				con.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
	
	public String bookAvailableRoom  (int userId,int roomNo) throws DAOException{
		String query1 = "insert into userInfo_Roominfo values (? , ?)";
		String query2 = "update roominfo set RoomStatus=? WHERE RoomNo=?";
		String str="";
		Connection con;
		try {
			con = jdbc.getConnection();
			PreparedStatement q2 = con.prepareStatement(query2);
			q2.setString(1, "unavailable");
			q2.setInt(2, roomNo);
			PreparedStatement q1 = con.prepareStatement(query1);
			q1.setInt(1, userId);
			q1.setInt(2, roomNo);
			int result1=q1.executeUpdate();
			int result2=q2.executeUpdate();
			if(result1==1&&result2==1){
				str= "Congratulations!! Your Room Has Been Booked";
			}
			return str;
		} catch (DatabaseException | SQLException e) {
			throw new DAOException(e);
		}
	}

}
