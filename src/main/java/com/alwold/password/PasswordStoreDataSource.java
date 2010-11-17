package com.alwold.password;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author alwold
 */
public class PasswordStoreDataSource implements DataSource {
	private DataSource dataSource;
	private Map<String, String> dataSourceParams;
	private String passwordService;
	private String passwordAccount;
	private String passwordStoreLocation;
	private boolean initialized;

	public PasswordStoreDataSource() {
		dataSourceParams = new HashMap<String, String>();
		initialized = false;
	}

	public void setDriverClass(String driverClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		dataSource = (DataSource) Class.forName(driverClass).newInstance();
	}

	public void setURL(String url) {
		dataSourceParams.put("URL", url);
	}

	public void setUser(String user) {
		dataSourceParams.put("user", user);
	}

	public void setPassword(String password) {
		dataSourceParams.put("password", password);
	}

	public void setPasswordService(String passwordService) {
		this.passwordService = passwordService;
	}

	public void setPasswordAccount(String passwordAccount) {
		this.passwordAccount = passwordAccount;
	}

	public void setPasswordStoreLocation(String passwordStoreLocation) {
		this.passwordStoreLocation = passwordStoreLocation;
	}

	public Connection getConnection() throws SQLException {
		ensureInitialized();
		return dataSource.getConnection();
	}

	public Connection getConnection(String string, String string1) throws SQLException {
		ensureInitialized();
		return dataSource.getConnection(string, string1);
	}

	public PrintWriter getLogWriter() throws SQLException {
		ensureInitialized();
		return dataSource.getLogWriter();
	}

	public void setLogWriter(PrintWriter writer) throws SQLException {
		ensureInitialized();
		dataSource.setLogWriter(writer);
	}

	public void setLoginTimeout(int i) throws SQLException {
		ensureInitialized();
		dataSource.setLoginTimeout(i);
	}

	public int getLoginTimeout() throws SQLException {
		ensureInitialized();
		return dataSource.getLoginTimeout();
	}

	public <T> T unwrap(Class<T> type) throws SQLException {
		ensureInitialized();
		return dataSource.unwrap(type);
	}

	public boolean isWrapperFor(Class<?> type) throws SQLException {
		ensureInitialized();
		return dataSource.isWrapperFor(type);
	}

	private synchronized void ensureInitialized() {
		if (!initialized) {
			try {
				for (String key: dataSourceParams.keySet()) {
					BeanUtils.setProperty(dataSource, key, dataSourceParams.get(key));
				}
				if (passwordAccount != null && passwordService != null) {
					PasswordStore store;
					if (passwordStoreLocation != null) {
						store = PasswordStoreFactory.getPasswordStore(passwordStoreLocation);
					} else {
						store = PasswordStoreFactory.getPasswordStore();
					}
					BeanUtils.setProperty(dataSource, "password", store.getPassword(passwordAccount, passwordService));
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (PasswordStoreException e) {
				throw new RuntimeException(e);
			}
			initialized = true;
		}
	}

}
