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
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {}
			try {
				Class.forName("com.sybase.jdbc.SybDriver");
			} catch (ClassNotFoundException e){}
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {}
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {}
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
			} catch (ClassNotFoundException e) {}
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		}
	}

	public boolean acceptsURL(String url) throws SQLException {
		if (url.startsWith("jdbc:pwstore:")) {
			return true;
		} else {
			return false;
		}
	}

	public Connection connect(String url, Properties info) throws SQLException {
		info = (Properties)info.clone();
		url = url.replace(":pwstore:", ":");
		try {
			String password = info.getProperty("password");
			String[] saNames = password.split("/");
			PasswordStore store = PasswordStoreFactory.getPasswordStore();
			password = store.getPassword(saNames[1], saNames[0]);
			info.setProperty("password", password);
			Driver driver = DriverManager.getDriver(url);
			return driver.connect(url, info);
		} catch (PasswordStoreException e) {
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