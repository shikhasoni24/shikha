package com.shikha.core.dao.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.UserCancelledBookedRoomDao;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.DatabaseException;
import com.shikha.core.utility.JDBCconnection;

@Component(service = UserCancelledBookedRoomDao.class, immediate = true)
public class UserCancelledBookedRoomDaoImpl implements UserCancelledBookedRoomDao {

	@Reference
	JDBCconnection jdbc;

	public String cancelBookedRoom(int userId, int roomNo) throws DAOException {
		String query1 = "DELETE FROM userInfo_Roominfo WHERE Room_No=?";
		;
		String query2 = "update roominfo set RoomStatus=? WHERE RoomNo=?";

		String str = "";
		Connection con = null;
		try {
			con = jdbc.getConnection();
			PreparedStatement q2 = con.prepareStatement(query2);
			q2.setString(1, "available");
			q2.setInt(2, roomNo);
			PreparedStatement q1 = con.prepareStatement(query1);
			q1.setInt(1, roomNo);
			int result1 = q1.executeUpdate();
			int result2 = q2.executeUpdate();

			if (result1 == 1 && result2 == 1) {
				str = "Your Room Has Been cancelled successfully";
			}
			return str;

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
