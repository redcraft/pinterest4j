package ru.redcraft.pinterest4j;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.Before;

import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestTestBase {

	protected TestUserInfo id1, idAutherror;
	protected PinterestFactory pinterestFactory;
	protected Pinterest pinterest1;
	private static final String BUNDLE_NAME = "test";
	private static final Logger log = Logger.getLogger(PinterestTestBase.class);
	
	protected class TestUserInfo {
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
	
	public PinterestTestBase() {
		ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
		id1 = getTestUserForPrefix(rb, "pinterest1");
		idAutherror = getTestUserForPrefix(rb, "autherror");
		pinterestFactory = new PinterestFactory();
	}
	
	private TestUserInfo getTestUserForPrefix(ResourceBundle rb, String prefix) {
		String login = rb.getString(prefix + ".login");
		String password = rb.getString(prefix + ".password");
		return new TestUserInfo(login, password);
	}
	
	@Before
	public void initialize() throws PinterestAuthException {
		pinterest1 = pinterestFactory.getInstance(id1.getLogin(), id1.getPassword());
	}
}
