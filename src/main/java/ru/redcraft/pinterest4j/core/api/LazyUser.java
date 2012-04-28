package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.managers.ManagerBundle;

public class LazyUser  implements User {
	
	private final String userName;
	
	public LazyUser(String userName, ManagerBundle managerBundle) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
}
