package ru.redcraft.pinterest.board;

import java.util.ArrayList;
import java.util.List;

import ru.redcraft.pinterest.core.BaseManager;
import ru.redcraft.pinterest.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest.interfaces.IPinterestAdtBoardInto;
import ru.redcraft.pinterest.interfaces.IPinterestBoard;
import ru.redcraft.pinterest.interfaces.IPinterestBoardManager;
import ru.redcraft.pinterest.interfaces.IPinterestCategory;
import ru.redcraft.pinterest.interfaces.IPinterestNewBoard;
import ru.redcraft.pinterest.interfaces.IPinterestPinManager;
import ru.redcraft.pinterest.internal.api.InternalAPIManager;

public class BoardManager extends BaseManager implements IPinterestBoardManager {

	private final IPinterestPinManager pinManager; 
	
	public  BoardManager(InternalAPIManager apiManager, IPinterestPinManager pinManager) {
		super(apiManager);
		this.pinManager = pinManager;
	}

	IPinterestPinManager getPinManager() {
		return pinManager;
	}
	
	public List<IPinterestBoard> getBoards() {
		List<IPinterestBoard> boards = new ArrayList<IPinterestBoard>();
		for(Board board : apiManager.getBoardAPI().getBoards()) {
			board.setBoardManager(this);
			boards.add(board);
		}
		return boards;
	}

	public IPinterestBoard createBoard(IPinterestNewBoard newBoard) throws PinterestBoardExistException {
		Board board = apiManager.getBoardAPI().createBoard(newBoard);
		board.setBoardManager(this);
		return board;
	}

	public IPinterestAdtBoardInto getAdditionalBoardInfo(IPinterestBoard board) {
		return apiManager.getBoardAPI().getAdditionalBoardInfo(board);
	}

	public void deleteBoard(IPinterestBoard board) {
		apiManager.getBoardAPI().deleteBoard(board);
	}

	public IPinterestBoard updateBoardInfo(IPinterestBoard board, String title,
			String description, IPinterestCategory category) {
		String newTitle = (title != null) ? title : board.getTitle();
		String newDescription = (description != null) ? description : board.getDescription();
		IPinterestCategory newCategory = (category != null) ? category : board.getCategory();
		Board newBoard = apiManager.getBoardAPI().updateBoardInfo(board, newTitle, newDescription, newCategory);
		newBoard.setBoardManager(this);
		return newBoard;
	}
}
