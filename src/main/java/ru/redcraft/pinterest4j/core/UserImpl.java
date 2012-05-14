package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.User;

public class UserImpl implements User {

	private final String userName;
	
	public UserImpl(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}
