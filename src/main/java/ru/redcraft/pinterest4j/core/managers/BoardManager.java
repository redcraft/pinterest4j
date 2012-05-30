package ru.redcraft.pinterest4j.core.managers;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.NewBoard;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public class BoardManager extends BaseManager {

	
	public  BoardManager(InternalAPIManager internalAPI) {
		super(internalAPI);
	}
	
	public List<Board> getBoards(User user) {
		return getApiManager().getBoardAPI().getBoards(user);
	}

	public Board createBoard(NewBoard newBoard) {
		return getApiManager().getBoardAPI().createBoard(newBoard);
	}

	public void deleteBoard(Board board) {
		getApiManager().getBoardAPI().deleteBoard(board);
	}

	public Board updateBoard(Board board, String title,	String description, BoardCategory category) {
		String newTitle = (title != null) ? title : board.getTitle();
		String newDescription = (description != null) ? description : board.getDescription();
		BoardCategory newCategory = (category != null) ? category : board.getCategory();
		return getApiManager().getBoardAPI().updateBoardInfo(board, newTitle, newDescription, newCategory);
	}

	public Board getBoardByURL(String url) {
		return getApiManager().getBoardAPI().getBoardByURL(url);
	}
}
