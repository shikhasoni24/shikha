package com.shikha.core.utility;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.shikha.core.exception.exceptionimpl.DatabaseException;

@Component(service = JDBCconnection.class, immediate = true)
public class JDBCconnection {

	private final Logger log = LoggerFactory.getLogger(JDBCconnection.class);

	@Reference
	DataSourcePool dataSourcePool;

	public Connection getConnection() throws DatabaseException {

		DataSource dataSource = null;
		Connection con = null;

		log.debug("Inside try");
		try {
			dataSource = (DataSource) dataSourcePool.getDataSource("Employee");
			con = dataSource.getConnection();
		} catch (DataSourceNotFoundException | SQLException e) {
			throw new DatabaseException(e);
		}

		log.debug("connection established");

		return con;
	}

}
