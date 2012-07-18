package ru.redcraft.pinterest4j;

import ru.redcraft.pinterest4j.core.api.AsyncPinPayload;



public interface Pinterest {

	//Boards
	
	Board getBoard(String url);
	
	Board createBoard(NewBoard newBoard);
	
	Board updateBoard(Board board, String title, String description, BoardCategory category);
	
	void deleteBoard(Board board);
	
	void followBoard(Board board);
	
	void unfollowBoard(Board board);
	
	boolean isFollowing(Board board);
	
	//Pin
	
	Pin addPin(Board board, NewPin newPin);
	
	void deletePin(Pin pin);
	
	Pin updatePin(Pin pin, String description, Double price, String link, Board board);
	
	Pin getPin(long id);
	
	void getAsyncPin(long id, AsyncPinPayload payload);
	
	Pin repin(Pin pin, Board board, String description);
	
	void likePin(Pin pin);
	
	void unlikePin(Pin pin);
	
	boolean isLiked(Pin pin);
	
	Comment addComment(Pin pin, String comment);
	
	void deleteComment(Comment comment);
	
	Iterable<Pin> getPinsByCategory(BoardCategory category);
	
	Iterable<Pin> getPopularPins();
	
	//User
	
	User getUser(String userName);
	
	User getUser();
	
	User updateUser(NewUserSettings settings);
	
	void followUser(User user);
	
	void unfollowUser(User user);
	
	boolean isFollowing(User user);
	
	//Gloabal
	
	void close();
	
}
