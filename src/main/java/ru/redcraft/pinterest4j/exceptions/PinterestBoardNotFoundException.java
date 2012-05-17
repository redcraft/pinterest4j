package ru.redcraft.pinterest4j.exceptions;

import ru.redcraft.pinterest4j.Board;

public class PinterestBoardNotFoundException extends PinterestRuntimeException {

	private static final String BOARD_NOT_FOUND_ERROR = "Board not found for given url: ";
	private static final long serialVersionUID = 2188271690918943112L;
	
	public PinterestBoardNotFoundException(Board board) {
		super(BOARD_NOT_FOUND_ERROR + board.getURL());
	}

}
