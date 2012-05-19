package ru.redcraft.pinterest4j;

import java.util.List;

import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;


public interface Pinterest {

	//Boards
	
	Board getBoardByURL(String url);
	
	List<Board> getBoardsForUser(User user);
	
	List<Board> getBoards();
	
	Board createBoard(NewBoard newBoard);
	
	Board updateBoard(Board board, String title,	String description, BoardCategory category);
	
	void deleteBoard(Board board);
	
	//Pin
	
	Pin addPinToBoard(Board board, NewPin newPin) throws PinMessageSizeException;
	
	List<Pin> getPins(Board board);
	
	List<Pin> getPins(Board board, int page);
	
	List<Pin> getPins(User user);
	
	List<Pin> getPins(User user, int page);
	
	List<Pin> getPins();
	
	List<Pin> getPins(int page);
	
	void deletePin(Pin pin);
	
	Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException;
	
	Pin getPinByID(long id);
	
	Pin repin(Pin pin, Board board, String description);
	
	Pin likePin(Pin pin);
	
	Pin unlikePin(Pin pin);
	
	Comment addComment(Pin pin, String comment);
	
	void deleteComment(Comment comment);
	
	List<Comment> getComments(Pin pin);
	
	//User
	
	User getUserForName(String userName);
	User getUser();
}
