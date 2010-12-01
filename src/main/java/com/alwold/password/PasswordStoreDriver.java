package com.alwold.password;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordStoreDriver implements Driver {
	private Driver driver;
	
	public PasswordStoreDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		driver = (Driver) Class.forName("oracle.jdbc.OracleDriver").newInstance();
	}

	public boolean acceptsURL(String url) throws SQLException {
		return driver.acceptsURL(url);
	}

	public Connection connect(String url, Properties info) throws SQLException {
		return driver.connect(url, info);
	}

	public int getMajorVersion() {
		return driver.getMajorVersion();
	}

	public int getMinorVersion() {
		return driver.getMinorVersion();
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return driver.getPropertyInfo(url, info);
	}

	public boolean jdbcCompliant() {
		return driver.jdbcCompliant();
	}
	
}