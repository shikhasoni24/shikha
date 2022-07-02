package com.shikha.core.dao.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.shikha.core.dao.UserDao;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.DatabaseException;
import com.shikha.core.utility.JDBCconnection;

@Component(service = UserDao.class, immediate = true)
public class UserDaoImpl implements UserDao {

	@Reference
	JDBCconnection jdbc;

	public String setUserInfo(String userName, String password,String emailid) throws DAOException {
		String query = "insert into userinfo (userName,emailId,password) values (? , ? , ?)";
		Connection con = null ;
		try {
			 con = jdbc.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);

			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, emailid);

			preparedStatement.setString(3, password);
			int i = preparedStatement.executeUpdate();
			// int userId=preparedStatement.getGeneratedKeys();
			if (i == 1) {
				return "User Is Registered ";
			} else {
				return "User Is Not Registered";
			}
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

	public List<User> getUserData() throws DAOException {

		List<User> userList = new ArrayList<>();
		String query = "select * from userInfo";
		Connection con = null; 
		try {
			 con = jdbc.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int userId = resultSet.getInt(1);
				String userName = resultSet.getString(2);
				String emailid=resultSet.getString(3);
				String password = resultSet.getString(4);
			
				userList.add(new User(userId, userName, password,emailid));
			}
			return userList;

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
