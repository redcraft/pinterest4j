package ru.redcraft.pinterest4j.core.managers;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;

public class PinManager extends BaseManager {

	public PinManager(InternalAPIManager internalAPI) {
		super(internalAPI);
	}

	public Pin addPinToBoard(Board board, NewPin newPin) throws PinMessageSizeException {
		return getApiManager().getPinAPI().addPinToBoard(board, newPin);
	}

	public List<Pin> getPins(User user) {
		return getApiManager().getPinAPI().getPins(user);
	}
	
	public List<Pin> getPins(User user, int page) {
		return getApiManager().getPinAPI().getPins(user, page);
	}

	public List<Pin> getPins(Board board) {
		return getApiManager().getPinAPI().getPins(board);
	}
	
	public List<Pin> getPins(Board board, int page) {
		return getApiManager().getPinAPI().getPins(board, page);
	}

	public void deletePin(Pin pin) {
		getApiManager().getPinAPI().deletePin(pin);
	}

	public Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException {
		String newDescription = (description != null) ? description : pin.getDescription();
		double newPrice = (price != null) ? price : pin.getPrice();
		String newLink = (link != null) ? link : pin.getLink();
		Board newBoard = (board != null) ? board : pin.getBoard();
		return getApiManager().getPinAPI().updatePin(pin, newDescription, newPrice, newLink, newBoard);
	}

	public Pin getPinByID(long id) {
		return getApiManager().getPinAPI().getPinByID(id);
	}

	public Pin repin(Pin pin, Board board, String description) {
		return getApiManager().getPinAPI().repin(pin, board, description);
	}

	public Pin likePin(Pin pin) {
		return getApiManager().getPinAPI().like(pin, true);
	}
	
	public Pin unlikePin(Pin pin) {
		return getApiManager().getPinAPI().like(pin, false);
	}

	public Comment addCommentToPin(Pin pin, String comment, User user) {
		return getApiManager().getPinAPI().addCommentToPin(pin, comment, user);
	}

	public void deleteComment(Comment comment) {
		getApiManager().getPinAPI().deleteComment(comment);
	}

	public List<Comment> getComments(Pin pin) {
		return getApiManager().getPinAPI().getComments(pin);
	}
}
