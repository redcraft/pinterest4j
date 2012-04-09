package ru.redcraft.pinterest.internal.api;

import ru.redcraft.pinterest.exceptions.PinterestAuthException;
import ru.redcraft.pinterest.exceptions.PinterestConnectionException;
import ru.redcraft.pinterest.exceptions.PinterestUserNotFoundException;

public class InternalAPIManager {

	private PinterestAccessToken accessToken = null;
	
	public InternalAPIManager(String login, String password) throws PinterestAuthException {
		accessToken = AuthAPI.authenticate(login, password);
	}
	
	public InternalAPIManager(String login) throws PinterestUserNotFoundException {
		accessToken = AuthAPI.validate(login);
	}
	
	public BoardAPI getBoardAPI() {
		BoardAPI boardAPI = null;
		try {
			boardAPI = new BoardAPI(accessToken);
		} catch(PinterestConnectionException connectionExc) {
			try {
				accessToken = AuthAPI.authenticate(accessToken);
			} catch (PinterestAuthException authExc) {
				throw connectionExc;
			}
		}
		return boardAPI;
	}
	
	public PinAPI getPinAPI() {
		PinAPI pinAPI = null;
		try {
			pinAPI = new PinAPI(accessToken);
		} catch(PinterestConnectionException connectionExc) {
			try {
				accessToken = AuthAPI.authenticate(accessToken);
			} catch (PinterestAuthException authExc) {
				throw connectionExc;
			}
		}
		return pinAPI;
	}
}
