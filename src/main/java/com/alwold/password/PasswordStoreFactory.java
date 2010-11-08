package com.alwold.password;

/**
 *
 * @author alwold
 */
public class PasswordStoreFactory {
	public static PasswordStore getPasswordStore() {
		return new PasswordSafePasswordStore();
	}

	public static PasswordStore getPasswordStore(String storeLocation) throws PasswordStoreException {
		if (storeLocation.endsWith("psafe3")) {
			return new PasswordSafePasswordStore(storeLocation);
		} else if (storeLocation.endsWith("keychain")) {
			return new OsXKeychainPasswordStore(storeLocation);
		} else {
			throw new PasswordStoreException("Cannot determine store type from filename");
		}
	}
}
