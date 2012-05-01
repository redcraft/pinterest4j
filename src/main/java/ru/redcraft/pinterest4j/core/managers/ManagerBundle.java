package ru.redcraft.pinterest4j.core.managers;

import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public class ManagerBundle {

	private final PinManager pinManager;
	private final BoardManager boardManager;
	private final UserManager userManager;
	
	public ManagerBundle(InternalAPIManager internalAPI) {
		pinManager = new PinManager(internalAPI);
		boardManager = new BoardManager(internalAPI);
		userManager = new UserManager(internalAPI);
	}

	public PinManager getPinManager() {
		return pinManager;
	}

	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
}
