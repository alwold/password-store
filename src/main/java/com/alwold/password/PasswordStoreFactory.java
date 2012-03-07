package com.alwold.password;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alwold
 */
public class PasswordStoreFactory {
	private static Map<String, PasswordStore> instances = new HashMap<String, PasswordStore>();
	private static PasswordStore instance;

	public static synchronized PasswordStore getPasswordStore() {
		if (instance == null) {
			instance = new PasswordSafePasswordStore();
		}
		return instance;
	}

	public static synchronized PasswordStore getPasswordStore(String storeLocation) throws PasswordStoreException {
		if (instances.get(storeLocation) == null) {
			if (storeLocation.endsWith("psafe3")) {
				instances.put(storeLocation, new PasswordSafePasswordStore(storeLocation));
			} else if (storeLocation.endsWith("keychain")) {
				instances.put(storeLocation, new OsXKeychainPasswordStore(storeLocation));
			} else {
				throw new PasswordStoreException("Cannot determine store type from filename");
			}
		}
		return instances.get(storeLocation);
	}
}
