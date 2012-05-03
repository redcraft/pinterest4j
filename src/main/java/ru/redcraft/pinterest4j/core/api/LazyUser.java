package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;

public class LazyUser  implements User {
	
	private final String userName;
	private final UserAPI userAPI;
	
	LazyUser(String userName, UserAPI userAPI) {
		this.userName = userName;
		this.userAPI = userAPI;
	}
	
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "LazyUser [userName=" + userName + "]";
	}
	
}
