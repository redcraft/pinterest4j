package ru.redcraft.pinterest.interfaces;

import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;

public interface IPinterestAuthAccount extends IPinterestAccount {

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException;
	
	public void deleteBoard(IPinterestBoard board);
	
	public void updateBoardInfo(IPinterestBoard board, String title, 
			String description, IPinterestCategory category);
}
