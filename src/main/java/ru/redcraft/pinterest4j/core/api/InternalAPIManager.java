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
}
