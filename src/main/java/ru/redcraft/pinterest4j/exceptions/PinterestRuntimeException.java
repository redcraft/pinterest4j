package ru.redcraft.pinterest4j.exceptions;

import com.sun.jersey.api.client.ClientResponse;

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
	
	public PinterestRuntimeException(ClientResponse response, String msg) {
		this(buildMsg(response.getStatus(), response.getEntity(String.class), msg));
	}
	
	public PinterestRuntimeException(ClientResponse response, String msg,  Exception e) {
		this(buildMsg(response.getStatus(), response.getEntity(String.class), msg), e);
	}
	
}
