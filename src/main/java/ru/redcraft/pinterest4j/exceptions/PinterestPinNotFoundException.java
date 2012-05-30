package ru.redcraft.pinterest4j.exceptions;


public class PinterestPinNotFoundException extends PinterestRuntimeException {

	private static final String PIN_NOT_FOUND_ERROR = "Pin not found for given ID: ";
	private static final long serialVersionUID = -2839063001100360668L;
	
	public PinterestPinNotFoundException(long pinID) {
		super(PIN_NOT_FOUND_ERROR + pinID);
	}

}
