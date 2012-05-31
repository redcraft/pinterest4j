package ru.redcraft.pinterest4j;

import java.util.UUID;

import ru.redcraft.pinterest4j.core.api.components.UserImpl;

public abstract class Utils {

	static public UserImpl getNonexistentUser(String name) {
		return new UserImpl(name, null, null, null, null, null, null, null,
				0, 0, 0, 0, 0);
	}
	
	static public UserImpl getNonexistentUser() {
		return getNonexistentUser(UUID.randomUUID().toString());
	}
}
