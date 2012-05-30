package ru.redcraft.pinterest4j;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestTestBase {

	protected static TestUserInfo id1, id2, idAutherror;
	protected static Pinterest pinterest1, pinterest2;
	protected static String imageLink;
	protected static String pinterest1Twitter;
	protected static String pinterest1Facebook;
	protected static File imageFile;
	protected static String webLink;
	protected static User nonexistentUser;
	private static final String BUNDLE_NAME = "test";
	private static final Logger log = Logger.getLogger(PinterestTestBase.class);
	
	protected static class TestUserInfo {
		private final String login;
		private final String password;
		
		TestUserInfo(String login, String password) {
			this.login = login;
			this.password = password;
			log.trace(String.format("Loaded config for user: ", this));
		}

		public String getLogin() {
			return login;
		}

		public String getPassword() {
			return password;
		}

		@Override
		public String toString() {
			return "TestUserInfo [login=" + login + ", password=" + password
					+ "]";
		}
		
	}
	
	static {
		ResourceBundle rb = null;
		try {
			rb = new PropertyResourceBundle(new FileInputStream("/opt/redcraft/pinterest4j.properties"));
		} catch (Exception e) {
			rb = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		id1 = getTestUserForPrefix(rb, "pinterest1");
		id2 = getTestUserForPrefix(rb, "pinterest2");
		idAutherror = getTestUserForPrefix(rb, "autherror");
		
		try {
			pinterest1 = PinterestFactory.getInstance(id1.getLogin(), id1.getPassword());
			pinterest2 = PinterestFactory.getInstance(id2.getLogin(), id2.getPassword());
		} catch (PinterestAuthException e) {
			log.error("Can't auth users", e);
			throw new RuntimeException(e);
		}
		
		pinterest1Twitter = rb.getString("pinterest1.twitter");
		pinterest1Facebook = rb.getString("pinterest1.facebook");
		
		imageLink = rb.getString("pin.image.link");
		webLink = rb.getString("pin.link");
		nonexistentUser = Utils.getNonexistentUser();
		try {
			imageFile = new File(ClassLoader.getSystemResource("pin_image.jpg").toURI());
		} catch(URISyntaxException e) {
			log.error("Can't load image file", e);
			throw new RuntimeException(e);
		}
	}
	
	private static TestUserInfo getTestUserForPrefix(ResourceBundle rb, String prefix) {
		String login = rb.getString(prefix + ".login");
		String password = rb.getString(prefix + ".password");
		return new TestUserInfo(login, password);
	}
	
}
