package ru.redcraft.pinterest4j.exceptions;


public class PinterestBoardExistException extends PinterestRuntimeException {

	private static final long serialVersionUID = -3407002348088799487L;

	public PinterestBoardExistException(String boardName) {
		super("You already have a board: " + boardName);
	}
}
