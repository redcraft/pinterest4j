package ru.redcraft.pinterest.exceptions;

public class PinterestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8763514955708359725L;

	public PinterestRuntimeException(String msg) {
		super(msg);
	}
	
	public PinterestRuntimeException(String msg, Exception e) {
		super(msg, e);
	}
}
