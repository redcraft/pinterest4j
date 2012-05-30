package ru.redcraft.pinterest4j.core.managers;

import ru.redcraft.pinterest4j.NewUserSettings;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public class UserManager extends BaseManager {

	public UserManager(InternalAPIManager internalAPI) {
		super(internalAPI);
	}
	
	public User getUserForName(String userName) {
		return getApiManager().getUserAPI().getUserForName(userName);
	}

	public User updateUser(NewUserSettings settings) {
		return getApiManager().getUserAPI().updateUser(settings);
	}
}
