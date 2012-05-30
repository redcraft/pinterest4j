package ru.redcraft.pinterest4j.exceptions;


public class PinterestUserNotFoundException extends PinterestRuntimeException {

	private static final String USER_NOT_FOUND_ERROR = "User not found for given name: ";
	private static final long serialVersionUID = -994217792594304557L;

	public PinterestUserNotFoundException(String userName) {
		super(USER_NOT_FOUND_ERROR + userName);
	}
}
