package ru.redcraft.pinterest.account;

import ru.redcraft.pinterest.exceptions.PinterestAuthException;
import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest.interfaces.IPinterestAuthAccount;
import ru.redcraft.pinterest.interfaces.IPinterestBoard;
import ru.redcraft.pinterest.interfaces.IPinterestCategory;
import ru.redcraft.pinterest.interfaces.IPinterestNewBoard;
import ru.redcraft.pinterest.internal.api.InternalAPIManager;

public class AuthAccount extends Account implements IPinterestAuthAccount {

	public AuthAccount(String login, String password) throws PinterestAuthException {
		super(login, new InternalAPIManager(login, password));
	}

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException {
		return boardManager.createBoard(newBoard);
	}

	public void deleteBoard(IPinterestBoard board) {
		boardManager.deleteBoard(board);
	}

	public IPinterestBoard updateBoardInfo(IPinterestBoard board, String title, 
			String description, IPinterestCategory category) {
		return boardManager.updateBoardInfo(board, title, 
				description, category);
	}
}
