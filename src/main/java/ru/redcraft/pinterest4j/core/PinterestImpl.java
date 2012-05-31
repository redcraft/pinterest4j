package ru.redcraft.pinterest4j.core;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.NewBoard;
import ru.redcraft.pinterest4j.NewPin;
import ru.redcraft.pinterest4j.NewUserSettings;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.Pinterest;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestImpl implements Pinterest {

	private final User user;
	private final InternalAPIManager internalAPI;
	
	public PinterestImpl(String login, String password) throws PinterestAuthException {
		internalAPI = new InternalAPIManager(login, password);
		this.user = internalAPI.getUserAPI().getUserForName(login);
	}

	public Board getBoard(String url) {
		return internalAPI.getBoardAPI().getBoardByURL(url);
	}
	
	public Pin addPin(Board board, NewPin newPin) throws PinMessageSizeException {
		return internalAPI.getPinAPI().addPinToBoard(board, newPin);
	}

	public List<Board> getBoards(User user) {
		return internalAPI.getBoardAPI().getBoards(user);
	}
	
	public List<Board> getBoards() {
		return getBoards(user);
	}

	public Board createBoard(NewBoard newBoard) {
		return internalAPI.getBoardAPI().createBoard(newBoard);
	}

	public Board updateBoard(Board board, String title, String description,
			BoardCategory category) {
		String newTitle = (title != null) ? title : board.getTitle();
		String newDescription = (description != null) ? description : board.getDescription();
		BoardCategory newCategory = (category != null) ? category : board.getCategory();
		return internalAPI.getBoardAPI().updateBoard(board, newTitle, newDescription, newCategory);
	}

	public void deleteBoard(Board board) {
		internalAPI.getBoardAPI().deleteBoard(board);
	}

	public User getUser(String userName) {
		return internalAPI.getUserAPI().getUserForName(userName);
	}

	public List<Pin> getPins(Board board) {
		return internalAPI.getPinAPI().getPins(board);
	}

	public List<Pin> getPins(Board board, int page) {
		return internalAPI.getPinAPI().getPins(board, page);
	}
	
	public List<Pin> getPins(User user) {
		return internalAPI.getPinAPI().getPins(user);
	}
	
	public List<Pin> getPins(User user, int page) {
		return internalAPI.getPinAPI().getPins(user, page);
	}
	
	public List<Pin> getPins() {
		return internalAPI.getPinAPI().getPins(user);
	}

	public List<Pin> getPins(int page) {
		return internalAPI.getPinAPI().getPins(user, page);
	}

	public void deletePin(Pin pin) {
		internalAPI.getPinAPI().deletePin(pin);
	}

	public Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException {
		String newDescription = (description != null) ? description : pin.getDescription();
		double newPrice = (price != null) ? price : pin.getPrice();
		String newLink = (link != null) ? link : pin.getLink();
		Board newBoard = (board != null) ? board : pin.getBoard();
		return internalAPI.getPinAPI().updatePin(pin, newDescription, newPrice, newLink, newBoard);
	}

	public User getUser() {
		return user;
	}

	public Pin getPin(long id) {
		return internalAPI.getPinAPI().getPinByID(id);
	}

	public Pin repin(Pin pin, Board board, String description) {
		return internalAPI.getPinAPI().repin(pin, board, description);
	}

	public void likePin(Pin pin) {
		internalAPI.getPinAPI().like(pin, true);
	}

	public void unlikePin(Pin pin) {
		internalAPI.getPinAPI().like(pin, false);
	}

	public Comment addComment(Pin pin, String comment) {
		return internalAPI.getPinAPI().addCommentToPin(pin, comment, user);
	}

	public void deleteComment(Comment comment) {
		internalAPI.getPinAPI().deleteComment(comment);
	}

	public List<Comment> getComments(Pin pin) {
		return internalAPI.getPinAPI().getComments(pin);
	}

	public User updateUser(NewUserSettings settings) {
		return internalAPI.getUserAPI().updateUser(settings);
	}

	public void followBoard(Board board) {
		internalAPI.getBoardAPI().followBoard(board, true);
	}
	
	public void unfollowBoard(Board board) {
		internalAPI.getBoardAPI().followBoard(board, false);
	}

	public void followUser(User user) {
		internalAPI.getUserAPI().followUser(user, true);
	}
	
	public void unfollowUser(User user) {
		internalAPI.getUserAPI().followUser(user, false);
	}

	public boolean isFollowing(Board board) {
		return internalAPI.getBoardAPI().isFollowing(board);
	}

	public boolean isFollowing(User user) {
		return internalAPI.getUserAPI().isFollowing(user);
	}

	public boolean isLiked(Pin pin) {
		return internalAPI.getPinAPI().isLiked(pin);
	}

}
