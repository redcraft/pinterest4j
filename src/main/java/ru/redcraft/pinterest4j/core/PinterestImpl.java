package ru.redcraft.pinterest4j.core;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.Pinterest;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;
import ru.redcraft.pinterest4j.core.managers.ManagerBundle;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestImpl implements Pinterest {

	private final User user;
	private final ManagerBundle managerBundle;
	
	public PinterestImpl(String login, String password) throws PinterestAuthException {
		InternalAPIManager internalAPI = new InternalAPIManager(login, password);
		managerBundle = new ManagerBundle(internalAPI);
		this.user = managerBundle.getUserManager().getUserForName(login);
	}

	public Board getBoard(String url) {
		return managerBundle.getBoardManager().getBoardByURL(url);
	}
	
	public Pin addPin(Board board, NewPin newPin) throws PinMessageSizeException {
		return managerBundle.getPinManager().addPinToBoard(board, newPin);
	}

	public List<Board> getBoards(User user) {
		return managerBundle.getBoardManager().getBoards(user);
	}
	
	public List<Board> getBoards() {
		return getBoards(user);
	}

	public Board createBoard(NewBoard newBoard) {
		return managerBundle.getBoardManager().createBoard(newBoard);
	}

	public Board updateBoard(Board board, String title, String description,
			BoardCategory category) {
		return managerBundle.getBoardManager().updateBoard(board, title, description, category);
	}

	public void deleteBoard(Board board) {
		managerBundle.getBoardManager().deleteBoard(board);
	}

	public User getUser(String userName) {
		return managerBundle.getUserManager().getUserForName(userName);
	}

	public List<Pin> getPins(Board board) {
		return managerBundle.getPinManager().getPins(board);
	}

	public List<Pin> getPins(Board board, int page) {
		return managerBundle.getPinManager().getPins(board, page);
	}
	
	public List<Pin> getPins(User user) {
		return managerBundle.getPinManager().getPins(user);
	}
	
	public List<Pin> getPins(User user, int page) {
		return managerBundle.getPinManager().getPins(user, page);
	}
	
	public List<Pin> getPins() {
		return managerBundle.getPinManager().getPins(user);
	}

	public List<Pin> getPins(int page) {
		return managerBundle.getPinManager().getPins(user, page);
	}

	public void deletePin(Pin pin) {
		managerBundle.getPinManager().deletePin(pin);
	}

	public Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException {
		return managerBundle.getPinManager().updatePin(pin, description, price, link, board);
	}

	public User getUser() {
		return user;
	}

	public Pin getPin(long id) {
		return managerBundle.getPinManager().getPinByID(id);
	}

	public Pin repin(Pin pin, Board board, String description) {
		return managerBundle.getPinManager().repin(pin, board, description);
	}

	public Pin likePin(Pin pin) {
		return managerBundle.getPinManager().likePin(pin);
	}

	public Pin unlikePin(Pin pin) {
		return managerBundle.getPinManager().unlikePin(pin);
	}

	public Comment addComment(Pin pin, String comment) {
		return managerBundle.getPinManager().addCommentToPin(pin, comment, user);
	}

	public void deleteComment(Comment comment) {
		managerBundle.getPinManager().deleteComment(comment);
	}

	public List<Comment> getComments(Pin pin) {
		return managerBundle.getPinManager().getComments(pin);
	}

	public User updateUser(UserSettings settings) {
		return managerBundle.getUserManager().updateUser(settings);
	}

}
