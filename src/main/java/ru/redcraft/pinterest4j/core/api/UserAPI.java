package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.UserImpl;

public class UserAPI extends CoreAPI {

	public UserAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}
	
	public UserImpl getCompleteUser(User user) {
		return null;
	}
	
	public User getUserForName(String userName) {
		return new LazyUser(userName, this);
	}
}
