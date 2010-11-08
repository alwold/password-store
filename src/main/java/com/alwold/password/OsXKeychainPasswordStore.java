package com.alwold.password;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author alwold
 */
public class OsXKeychainPasswordStore implements PasswordStore {
	private String keychainName;

	public OsXKeychainPasswordStore() {
		this.keychainName = "jetty.keychain";
	}
	public OsXKeychainPasswordStore(String storeLocation) {
		this.keychainName = storeLocation;
	}

	public String getPassword(String account, String service) throws PasswordStoreException {
		CommandLine cmd = new CommandLine("security");
		cmd.addArgument("find-generic-password");
		cmd.addArgument("-a");
		cmd.addArgument(account);
		cmd.addArgument("-s");
		cmd.addArgument(service);
		cmd.addArgument("-g");
		cmd.addArgument(keychainName);
		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(System.out, baos, System.in));
		executor.setExitValue(0);
		try {
			executor.execute(cmd);
			String password = null;
			for (String line: baos.toString().split("\n")) {
				if (line.startsWith("password:")) {
					String[] fields = line.split(":");
					password = fields[1].trim();
					// strip quotes off beginning and end
					password = password.substring(1);
					password = password.substring(0, password.length()-1);
				}
			}
			return password;
		} catch (ExecuteException e) {
			throw new PasswordStoreException(e);
		} catch (IOException e) {
			throw new PasswordStoreException(e);
		}
	}

}
