package com.alwold.password;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordStoreDriver implements Driver {
	static {
		try {
			DriverManager.registerDriver(new PasswordStoreDriver());
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		}
		// TODO initialize drivers: oracle, sybase, mysql, mssql, jtds
	}
	
	public boolean acceptsURL(String url) throws SQLException {
		if (url.startsWith("jdbc:pwstore:")) {
			return true;
		} else {
			return false;
		}
	}

	public Connection connect(String url, Properties info) throws SQLException {
		url = url.replace(":pwstore:", ":");
		try {
			// TODO replace the password value in info with the real password
			return ((Driver) Class.forName(info.getProperty("realDriver")).newInstance()).connect(url, info);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 0;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[]{
			new DriverPropertyInfo("user", info.getProperty("user")), 
			new DriverPropertyInfo("password", info.getProperty("password"))};
	}

	public boolean jdbcCompliant() {
		return false;
	}
	
}