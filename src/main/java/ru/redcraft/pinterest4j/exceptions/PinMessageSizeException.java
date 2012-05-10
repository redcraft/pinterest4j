package ru.redcraft.pinterest4j.exceptions;

public class PinMessageSizeException extends PinterestException {

	private static final long serialVersionUID = -170707100325210641L;
	public static final String MSG_TOO_LONG = "Description message is greater then 500 characters";
	public static final String MSG_ZERO_SIZE = "Description message length is 0";

	public PinMessageSizeException(String msg) {
		super(msg);
		
	}
	
}
