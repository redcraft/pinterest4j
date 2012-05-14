package ru.redcraft.pinterest4j.core.api;

import static org.junit.Assert.*;

import javax.ws.rs.core.Cookie;

import org.junit.Test;

public class PinterestAccessTokenTest {

	private static final String CSRFTOKEN = "0f4819eaf9003f64e9b574431dbf471d";
	private static final String SESSION = "eJwljEELgjAYQP9NFy+a2G3EYJEW30fCLnUZoWNtrKxN29" +
			"yvT+n63uO5Or7q7wWyu8mOH0etZY8qhN25CWQvZHxrJz0BDglTUwI7FNgSshGjfso/NtctcphRLdgOSsleDNNI+vpU" +
			"3NbUS+91T8BAhayJYNoc1nby0olVcMiR0Rw5LZdRiambwXTVD9QNM1k=";
	
	@Test
	public void BasicPinterestAccessTokenTest() {
		Cookie csrfToken = new Cookie("csrftoken", CSRFTOKEN);
		Cookie sessionToken = new Cookie("_pinterest_sess", SESSION);
		String login = "some_login";
		String password  = "some_password";
		PinterestAccessToken token = new PinterestAccessToken(login, password, csrfToken, sessionToken);
		assertEquals(login, token.getLogin());
		assertEquals(password, token.getPassword());
		assertEquals(csrfToken, token.getCsrfToken());
		assertEquals(sessionToken, token.getSessionToken());
	}

}
