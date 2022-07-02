package com.shikha.core.dao.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.dao.ForgotPassword;

import com.shikha.core.exception.exceptionimpl.DAOException;
import com.shikha.core.exception.exceptionimpl.DatabaseException;
import com.shikha.core.utility.JDBCconnection;

@Component(service = ForgotPassword.class, immediate = true)
public class ForgotPasswordImpl implements ForgotPassword {

	@Reference
	JDBCconnection jdbc;

	public String resetPassword(String userName,String newPassword) throws DAOException {

		String query = "update userinfo set Password=? WHERE UserName=?";
		Connection con = null;
		try {
			con = jdbc.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);

			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, userName);
			int i = preparedStatement.executeUpdate();
			// int userId=preparedStatement.getGeneratedKeys();
			if (i == 1) {
				return "Password has been resetted successfully";
			} else {
				return "Password has not been resetted.";
			}
		} catch (DatabaseException | SQLException e) {
			throw new DAOException(e);
		} finally {

			try {
				con.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}

	}
}
