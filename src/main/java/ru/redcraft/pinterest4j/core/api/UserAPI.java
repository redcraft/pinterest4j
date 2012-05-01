package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;

public class UserAPI extends CoreAPI {

	public UserAPI(PinterestAccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	public User getUserForName(String userName) {
		return new LazyUser(userName, this);
	}
}
