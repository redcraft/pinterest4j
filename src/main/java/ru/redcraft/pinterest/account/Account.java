package ru.redcraft.pinterest.account;

import java.util.List;

import ru.redcraft.pinterest.board.BoardManager;
import ru.redcraft.pinterest.exceptions.PinterestUserNotFoundException;
import ru.redcraft.pinterest.interfaces.IPinterestAccount;
import ru.redcraft.pinterest.interfaces.IPinterestBoard;
import ru.redcraft.pinterest.interfaces.IPinterestBoardManager;
import ru.redcraft.pinterest.interfaces.IPinterestPinManager;
import ru.redcraft.pinterest.internal.api.InternalAPIManager;
import ru.redcraft.pinterest.pin.PinManager;

public class Account implements IPinterestAccount {

	protected final String login;
	protected final IPinterestBoardManager boardManager;
	protected final IPinterestPinManager pinManager;
	
	protected Account(String login, InternalAPIManager apiManager) {
		this.login = login;
		pinManager = new PinManager(apiManager);
		boardManager = new BoardManager(apiManager, pinManager);
	}
	
	public Account(String login) throws PinterestUserNotFoundException {
		this(login, new InternalAPIManager(login));
	}

	public String getLogin() {
		return login;
	}
	
	public List<IPinterestBoard> getBoards() {
		return boardManager.getBoards();
	}
}
