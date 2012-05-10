package ru.redcraft.pinterest4j.core.managers;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
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
		Pin pin = apiManager.getPinAPI().addPinToBoard(board, newPin);
		return pin;
	}

	public List<Pin> getPins(User user) {
		return apiManager.getPinAPI().getPins(user);
	}
	
	public List<Pin> getPins(User user, int page) {
		return apiManager.getPinAPI().getPins(user, page);
	}

	public List<Pin> getPins(Board board) {
		return apiManager.getPinAPI().getPins(board);
	}
	
	public List<Pin> getPins(Board board, int page) {
		return apiManager.getPinAPI().getPins(board, page);
	}

	public void deletePin(Pin pin) {
		apiManager.getPinAPI().deletePin(pin);
	}

	public Pin updatePin(Pin pin, String description, Double price, String link, Board board) throws PinMessageSizeException {
		String newDescription = (description != null) ? description : pin.getDescription();
		double newPrice = (price != null) ? price : pin.getPrice();
		String newLink = (link != null) ? link : pin.getLink();
		Board newBoard = (board != null) ? board : pin.getBoard();
		return apiManager.getPinAPI().updatePin(pin, newDescription, newPrice, newLink, newBoard);
	}
}
