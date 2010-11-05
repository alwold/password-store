package com.alwold.password;

/**
 *
 * @author alwold
 */
public interface PasswordStore {
	String getPassword(String account, String service) throws PasswordStoreException;
}
