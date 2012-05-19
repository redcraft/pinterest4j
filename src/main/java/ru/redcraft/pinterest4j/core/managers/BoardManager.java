package ru.redcraft.pinterest4j.core.managers;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;

public class BoardManager extends BaseManager {

	
	public  BoardManager(InternalAPIManager internalAPI) {
		super(internalAPI);
	}
	
	public List<Board> getBoards(User user) {
		return apiManager.getBoardAPI().getBoards(user);
	}

	public Board createBoard(NewBoard newBoard) {
		return apiManager.getBoardAPI().createBoard(newBoard);
	}

	public void deleteBoard(Board board) {
		apiManager.getBoardAPI().deleteBoard(board);
	}

	public Board updateBoard(Board board, String title,	String description, BoardCategory category) {
		String newTitle = (title != null) ? title : board.getTitle();
		String newDescription = (description != null) ? description : board.getDescription();
		BoardCategory newCategory = (category != null) ? category : board.getCategory();
		return apiManager.getBoardAPI().updateBoardInfo(board, newTitle, newDescription, newCategory);
	}

	public Board getBoardByURL(String url) {
		return apiManager.getBoardAPI().getBoardByURL(url);
	}
}
