package ru.redcraft.pinterest.interfaces;

import java.util.List;

import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;

public interface IPinterestBoardManager {

	public List<IPinterestBoard> getBoards();

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException;
	
	public IPinterestAdtBoardInto getAdditionalBoardInfo(IPinterestBoard board);

	public void deleteBoard(IPinterestBoard board);

	public IPinterestBoard updateBoardInfo(IPinterestBoard board, String title,
			String description, IPinterestCategory category);
}
