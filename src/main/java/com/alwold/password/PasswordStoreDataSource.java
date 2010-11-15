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

	public PasswordStoreDataSource() {
		dataSourceParams = new HashMap<String, String>();
	}

	public void setDriverClass(String driverClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		dataSource = (DataSource) Class.forName(driverClass).newInstance();
		for (String key: dataSourceParams.keySet()) {
			BeanUtils.setProperty(dataSource, key, dataSourceParams.get(key));
		}
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public Connection getConnection(String string, String string1) throws SQLException {
		return dataSource.getConnection(string, string1);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	public void setLogWriter(PrintWriter writer) throws SQLException {
		dataSource.setLogWriter(writer);
	}

	public void setLoginTimeout(int i) throws SQLException {
		dataSource.setLoginTimeout(i);
	}

	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	public <T> T unwrap(Class<T> type) throws SQLException {
		return dataSource.unwrap(type);
	}

	public boolean isWrapperFor(Class<?> type) throws SQLException {
		return dataSource.isWrapperFor(type);
	}

}
