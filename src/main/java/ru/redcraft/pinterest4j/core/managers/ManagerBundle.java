package ru.redcraft.pinterest4j.core.managers;

import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public class ManagerBundle {

	private final PinManager pinManager;
	private final BoardManager boardManager;
	
	public ManagerBundle(InternalAPIManager internalAPI) {
		pinManager = new PinManager(internalAPI);
		boardManager = new BoardManager(internalAPI);
	}

	public PinManager getPinManager() {
		return pinManager;
	}

	public BoardManager getBoardManager() {
		return boardManager;
	}
	
}
