package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.core.BoardCategoryImpl;
import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardNotFoundException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

public class BoardMethodsTest extends PinterestTestBase {
	
	@Test
	public void boardLifeCircleTest() throws PinterestBoardExistException {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategoryImpl.DESIGN;
		
		//Create
		NewBoard newBoard = new NewBoard(newTitle, newCategory);
		Board createdBoard = pinterest1.createBoard(newBoard);
		List<Board> boards = pinterest1.getBoards();
		boolean boardCreated = false;
		for(Board board : boards) {
			if(board.getTitle().equals(newTitle) && board.getCategory().equals(newCategory)) {
				boardCreated = true;
				break;
			}
		}
		assertTrue("Board is not created", boardCreated);
		
		//Update
		newTitle = UUID.randomUUID().toString();
		String newDescription = UUID.randomUUID().toString();
		newCategory = BoardCategoryImpl.CARS_AND_MOTORCYCLES;
		Board updatedBoard = pinterest1.updateBoard(createdBoard, newTitle, newDescription, newCategory);
		boards = pinterest1.getBoards();
		boolean boardUpdated = false;
		for(Board board : boards) {
			if(board.getTitle().equals(newTitle) && board.getCategory().equals(newCategory) &&
			   board.getDescription().equals(newDescription)) {
				boardUpdated = true;
				break;
			}
		}
		assertTrue("Board is not updated", boardUpdated);
		
		//Delete
		pinterest1.deleteBoard(updatedBoard);
		boards = pinterest1.getBoards();
		boolean boardDeleted = true;
		for(Board board : boards) {
			if(board.getTitle().equals(newTitle)) {
				boardDeleted = false;
				break;
			}
		}
		assertTrue("Board is not deleted", boardDeleted);
	}
	
	@Test
	public void boardUpdateWithoutParameters() throws PinterestBoardExistException {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategoryImpl.DESIGN;
		NewBoard newBoard = new NewBoard(newTitle, newCategory);
		Board createdBoard = pinterest1.createBoard(newBoard);
		Board updatedBoard = pinterest1.updateBoard(createdBoard, null, null, null);
		assertEquals(newTitle, updatedBoard.getTitle());
		assertEquals(newCategory, updatedBoard.getCategory());
		assertEquals("", updatedBoard.getDescription());
		pinterest1.deleteBoard(updatedBoard);
	}
	
	@Test
	public void boardExistExceptionTest() {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategoryImpl.DESIGN;
		NewBoard newBoard = new NewBoard(newTitle, newCategory);
		Board createdBoard = null;
		boolean exceptionRised = false;
		try {
			createdBoard = pinterest1.createBoard(newBoard);
			pinterest1.createBoard(newBoard);
		} catch(PinterestBoardExistException e) {
			exceptionRised = true;
			pinterest1.deleteBoard(createdBoard);
		}
		assertTrue("No exception rised", exceptionRised);
	}
	
	@Test(expected=PinterestRuntimeException.class)
	public void getBoarsExceptionTest() {
		pinterest1.getBoardsForUser(nonexistentUser);
	}
	
	@Test(expected=PinterestBoardNotFoundException.class)
	public void getUnexistentBoardTest() {
		pinterest1.getBoardByURL(UUID.randomUUID().toString());
	}
	
}
