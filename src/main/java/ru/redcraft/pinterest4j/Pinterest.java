package ru.redcraft.pinterest4j;

import java.util.List;

import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;


public interface Pinterest {

	public List<Board> getBoardsForUser(User user);
	
	public Board createBoard(NewBoard newBoard) throws PinterestBoardExistException;
	
	public Board updateBoard(Board board, String title,	String description, BoardCategory category);
	
	public void deleteBoard(Board board);
	
	public Pin addPinToBoard(long boardID, NewPin newPin);
	
	public User getUserForName(String userName);
}
