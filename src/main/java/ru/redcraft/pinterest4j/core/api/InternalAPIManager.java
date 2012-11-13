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

import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class InternalAPIManager {

	private final UserAPI userAPI;
	private final BoardAPI boardAPI;
	private final PinAPI pinAPI;
	
	public InternalAPIManager(String login, String password) throws PinterestAuthException {
		PinterestAccessToken accessToken = AuthAPI.authenticate(login, password);
		userAPI = new UserAPI(accessToken, this);
		boardAPI = new BoardAPI(accessToken, this);
		pinAPI = new PinAPI(accessToken, this);
	}
	
	public BoardAPI getBoardAPI() {
		return boardAPI;
	}
	
	public PinAPI getPinAPI() {
		return pinAPI;
	}
	
	public UserAPI getUserAPI() {
		return userAPI;
	}

	public void close() {
		userAPI.close();
		boardAPI.close();
		pinAPI.close();
	}
}
