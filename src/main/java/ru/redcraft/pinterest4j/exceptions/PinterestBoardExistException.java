package ru.redcraft.pinterest4j.exceptions;

import ru.redcraft.pinterest4j.core.NewBoard;

public class PinterestBoardExistException extends PinterestRuntimeException {

	private static final long serialVersionUID = -3407002348088799487L;

	public PinterestBoardExistException(NewBoard newBoard) {
		super("You already have a board: " + newBoard);
	}
}
