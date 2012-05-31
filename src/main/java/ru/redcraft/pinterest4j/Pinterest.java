package ru.redcraft.pinterest4j;

import java.util.List;

import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;


public interface Pinterest {

	//Boards
	
	Board getBoard(String url);
	
	List<Board> getBoards(User user);
	
	List<Board> getBoards();
	
	Board createBoard(NewBoard newBoard);
	
	Board updateBoard(Board board, String title, String description, BoardCategory category);
	
	void deleteBoard(Board board);
	
	void followBoard(Board board);
	
	void unfollowBoard(Board board);
	
	boolean isFollowing(Board board);
	
	//Pin
	
	Pin addPin(Board board, NewPin newPin) throws PinMessageSizeException;
	
	List<Pin> getPins(Board board);
	
	List<Pin> getPins(Board board, int page);
	
	List<Pin> getPins(User user);
	
	List<Pin> getPins(User user, int page);
	
	List<Pin> getPins();
	
	List<Pin> getPins(int page);
	
	void deletePin(Pin pin);
	
	Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException;
	
	Pin getPin(long id);
	
	Pin repin(Pin pin, Board board, String description);
	
	void likePin(Pin pin);
	
	void unlikePin(Pin pin);
	
	boolean isLiked(Pin pin);
	
	Comment addComment(Pin pin, String comment);
	
	void deleteComment(Comment comment);
	
	List<Comment> getComments(Pin pin);
	
	//User
	
	User getUser(String userName);
	
	User getUser();
	
	User updateUser(NewUserSettings settings);
	
	void followUser(User user);
	
	void unfollowUser(User user);
	
	boolean isFollowing(User user);
	
}
