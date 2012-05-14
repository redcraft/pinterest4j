package ru.redcraft.pinterest4j.exceptions;

public class PinterestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8763514955708359725L;

	private static String buildMsg(int status, String response, String msg) {
		return String.format("Runtime error: %s. Response code: %d. Response: %s", msg, status, response);
	}
	
	public PinterestRuntimeException(String msg) {
		super(msg);
	}
	
	public PinterestRuntimeException(String msg, Exception e) {
		super(msg, e);
	}
	
	public PinterestRuntimeException(int status, String response, String msg) {
		this(buildMsg(status, response, msg));
	}
	
	public PinterestRuntimeException(int status, String response, String msg,  Exception e) {
		this(buildMsg(status, response, msg), e);
	}
	
}
