package ru.redcraft.pinterest4j.exceptions;

import ru.redcraft.pinterest4j.User;

public class PinterestUserNotFoundException extends PinterestRuntimeException {

	private static final String USER_NOT_FOUND_ERROR = "User not found for given name: ";
	private static final long serialVersionUID = -994217792594304557L;

	public PinterestUserNotFoundException(User user) {
		super(USER_NOT_FOUND_ERROR + user.getUserName());
	}
}
