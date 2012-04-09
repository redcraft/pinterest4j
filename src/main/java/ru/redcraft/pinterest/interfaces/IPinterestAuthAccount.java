package ru.redcraft.pinterest.interfaces;

import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;

public interface IPinterestAuthAccount extends IPinterestAccount {

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException;
	
}
