package com.alwold.password;

/**
 *
 * @author alwold
 */
public class PasswordStoreFactory {
	public static PasswordStore getPasswordStore() {
		return new OsXKeychainPasswordStore();
	}

}
