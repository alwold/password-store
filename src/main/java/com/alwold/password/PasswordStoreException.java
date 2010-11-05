package com.alwold.password;

/**
 *
 * @author alwold
 */
public class PasswordStoreException extends Exception {

	public PasswordStoreException(Throwable t) {
		super(t);
	}

	public PasswordStoreException(String message, Throwable t) {
		super(message, t);
	}

	public PasswordStoreException(String message) {
		super(message);
	}

	public PasswordStoreException() {
		super();
	}

}
