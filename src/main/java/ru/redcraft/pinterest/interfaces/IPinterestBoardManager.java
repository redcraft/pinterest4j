package ru.redcraft.pinterest.interfaces;

import java.util.List;

import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;

public interface IPinterestBoardManager {

	public List<IPinterestBoard> getBoards();

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException;
	
	public IPinterestAdtBoardInto getAdditionalBoardInfo(IPinterestBoard board);
}
