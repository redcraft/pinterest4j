package ru.redcraft.pinterest.exceptions;

import ru.redcraft.pinterest.interfaces.IPinterestNewBoard;

public class PinterestBoardExistException extends PinterestException {

	private static final long serialVersionUID = -3407002348088799487L;

	public PinterestBoardExistException(IPinterestNewBoard newBoard) {
		super("You already have a board: " + newBoard);
	}
}
