package com.alwold.password;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import org.apache.log4j.Logger;
import org.pwsafe.lib.exception.EndOfFileException;
import org.pwsafe.lib.exception.InvalidPassphraseException;
import org.pwsafe.lib.exception.UnsupportedFileVersionException;
import org.pwsafe.lib.file.PwsFieldTypeV3;
import org.pwsafe.lib.file.PwsFile;
import org.pwsafe.lib.file.PwsFileFactory;
import org.pwsafe.lib.file.PwsRecord;
import org.pwsafe.lib.file.PwsStringUnicodeField;

/**
 *
 * @author alwold
 */
public class PasswordSafePasswordStore implements PasswordStore {
	private static final Logger logger = Logger.getLogger(PasswordSafePasswordStore.class);
	private String masterPassword;
	private String passwordSafeFile;

	public PasswordSafePasswordStore() {
		this(System.getProperty("user.home")+File.separator+"pwsafe.psafe3");
	}

	public PasswordSafePasswordStore(String passwordSafeFile) {
		this.passwordSafeFile = passwordSafeFile;
	}

	public String getPassword(String account, String service) throws PasswordStoreException {
		try {
			StringBuilder sb = new StringBuilder();
			if (masterPassword == null) {
				JPasswordField field = new JPasswordField(20);
				JPanel panel = new JPanel();
				panel.add(new JLabel("Password: "));
				panel.add(field);
				if (JOptionPane.showConfirmDialog(null, panel, "PasswordSafe login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new Icon() {

					public void paintIcon(Component cmpnt, Graphics grphcs, int i, int i1) {}

					public int getIconWidth() {
						return 0;
					}

					public int getIconHeight() {
						return 0;
					}
				}) < 0) {
					throw new PasswordStoreException("No password entered");
				} else {
					masterPassword = new String(field.getPassword());
				}
			}
			sb.append(masterPassword);
			PwsFile file = PwsFileFactory.loadFile(passwordSafeFile, sb);
			for (Iterator<? extends PwsRecord> i = file.getRecords(); i.hasNext(); ) {
				PwsRecord record = i.next();
				logger.trace("title = "+((PwsStringUnicodeField) record.getField(PwsFieldTypeV3.TITLE)).getValue());
				logger.trace("username = "+((PwsStringUnicodeField) record.getField(PwsFieldTypeV3.USERNAME)).getValue());
				if (((PwsStringUnicodeField) record.getField(PwsFieldTypeV3.TITLE)).getValue().equals(service) &&
						((PwsStringUnicodeField) record.getField(PwsFieldTypeV3.USERNAME)).getValue().equals(account)) {
					logger.trace("found it");
					return (String)((PwsStringUnicodeField) record.getField(PwsFieldTypeV3.PASSWORD)).getValue();
				}
//				for (Iterator<Integer> j = record.getFields(); j.hasNext(); ) {
//					PwsField field = record.getField(j.next());
//
//				}
			}
		} catch (EndOfFileException ex) {
			throw new PasswordStoreException(ex);
		} catch (FileNotFoundException ex) {
			throw new PasswordStoreException(ex);
		} catch (InvalidPassphraseException ex) {
			throw new PasswordStoreException(ex);
		} catch (IOException ex) {
			throw new PasswordStoreException(ex);
		} catch (UnsupportedFileVersionException ex) {
			throw new PasswordStoreException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new PasswordStoreException(ex);
		}
		return null;
	}

}
