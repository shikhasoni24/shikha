package com.shikha.core.dao.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.shikha.core.dao.UserBookedRooms;
import com.shikha.core.entity.UserRoom;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.DatabaseException;
import com.shikha.core.utility.JDBCconnection;

@Component(service = UserBookedRooms.class, immediate = true)
public class UserBookedRoomsImpl implements UserBookedRooms {

	@Reference
	JDBCconnection jdbc;
	public List<UserRoom> getAllRoomsBookedByUser() throws DAOException{
				List<UserRoom> userRoom=new ArrayList<>();
				String query="select * from userInfo_roominfo";
				Connection con = null;
				try {
					con = jdbc.getConnection();
					PreparedStatement p=con.prepareStatement(query);
					ResultSet res=p.executeQuery();
					while(res.next()){
						int userid=res.getInt(1);
						int roomNo=res.getInt(2);
						userRoom.add(new UserRoom(userid,roomNo));
					}
					return userRoom;
				} catch (DatabaseException | SQLException e) {
					throw new DAOException(e);
				}
				finally{
					
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						throw new DAOException(e);
					}
				}
	
				
			}
	
	
}
