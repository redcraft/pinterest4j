package ru.redcraft.pinterest4j.core.api;

import javax.ws.rs.core.Cookie;

public class PinterestAccessToken {

	private final String email;
	private final String password;
	private final Cookie csrfToken;
	private final Cookie sessionToken;
	
	PinterestAccessToken(String email, String password, Cookie csrfToken, Cookie sessionToken) {
		this.email = email;
		this.password = password;
		this.csrfToken = csrfToken;
		this.sessionToken = sessionToken;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	public Cookie getCsrfToken() {
		return csrfToken;
	}
	
	public Cookie getSessionToken() {
		return sessionToken;
	} 
	
	
	@Override
	public String toString() {
		return "PinterestAccessToken [email=" + email + ", csrfToken=" +
				csrfToken + ", sessionToken=" + sessionToken + "]";
	}

	public String generateCookieHeader() {
		return String.format("%s=%s; %s=%s", 
				csrfToken.getName(), csrfToken.getValue(), 
				sessionToken.getName(), sessionToken.getValue());
	}
}
