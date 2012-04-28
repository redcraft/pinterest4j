package ru.redcraft.pinterest4j.exceptions;

public class PinterestException extends Exception {

	private static final long serialVersionUID = -183941429788910081L;

	public PinterestException() {};
	
	public PinterestException(String msg) {
		super(msg);
	}
}
