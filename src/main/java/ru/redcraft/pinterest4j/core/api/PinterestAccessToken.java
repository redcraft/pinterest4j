/**
 * Copyright (C) 2012 Maxim Gurkin <redmax3d@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
