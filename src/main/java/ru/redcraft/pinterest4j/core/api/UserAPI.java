package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;

public class UserAPI extends CoreAPI {

	public UserAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}
	
	public User getUserForName(String userName) {
		return new LazyUser(userName, this);
	}
}
