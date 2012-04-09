package ru.redcraft.pinterest.internal.api;

import javax.ws.rs.core.Cookie;

public class PinterestAccessToken {

	private final String login;
	private final String password;
	private final Cookie csrfToken;
	private final Cookie sessionToken;
	private final boolean authenticated;
	
	public PinterestAccessToken(String login, String password, Cookie csrfToken, Cookie sessionToken) {
		this.login = login;
		this.password = password;
		this.csrfToken = csrfToken;
		this.sessionToken = sessionToken;
		this.authenticated = true;
	}
	
	public PinterestAccessToken(String login) {
		this.login = login;
		this.password = null;
		this.csrfToken = null;
		this.sessionToken = null;
		this.authenticated = false;
	}
	
	public String getLogin() {
		return login;
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
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	@Override
	public String toString() {
		return "PinterestAccessToken [login=" + login + ", password="
				+ password + ", csrfToken=" + csrfToken + ", sessionToken="
				+ sessionToken + ", authenticated=" + authenticated + "]";
	}

	public String generateCookieHeader() {
		return String.format("%s=%s; %s=%s", 
				csrfToken.getName(), csrfToken.getValue(), 
				sessionToken.getName(), sessionToken.getValue());
	}
}
