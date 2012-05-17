package ru.redcraft.pinterest4j;

import java.util.List;

import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;


public interface Pinterest {

	//Boards
	
	public Board getBoardByURL(String url);
	
	public List<Board> getBoardsForUser(User user);
	
	public List<Board> getBoards();
	
	public Board createBoard(NewBoard newBoard) throws PinterestBoardExistException;
	
	public Board updateBoard(Board board, String title,	String description, BoardCategory category);
	
	public void deleteBoard(Board board);
	
	//Pin
	
	public Pin addPinToBoard(Board board, NewPin newPin) throws PinMessageSizeException;
	
	public List<Pin> getPins(Board board);
	
	public List<Pin> getPins(Board board, int page);
	
	public List<Pin> getPins(User user);
	
	public List<Pin> getPins(User user, int page);
	
	public List<Pin> getPins();
	
	public List<Pin> getPins(int page);
	
	public void deletePin(Pin pin);
	
	public Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException;
	
	public Pin getPinByID(long id);
	
	public Pin repin(Pin pin, Board board, String description);
	
	public Pin likePin(Pin pin);
	
	public Pin unlikePin(Pin pin);
	
	//User
	
	public User getUserForName(String userName);
	public User getUser();
}
