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
import ru.redcraft.pinterest.internal.api.InternalAPIManager;

public class BoardManager extends BaseManager implements IPinterestBoardManager {

	public  BoardManager(InternalAPIManager apiManager) {
		super(apiManager);
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

	public void updateBoardInfo(IPinterestBoard board, String title,
			String description, IPinterestCategory category) {
	}
}
