package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.core.NewBoardImpl;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardNotFoundException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

public class BoardMethodsTest extends PinterestTestBase {
	
	@Test
	public void boardLifeCircleTest() {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategory.DESIGN;
		
		//Create
		NewBoardImpl newBoard = new NewBoardImpl(newTitle, newCategory);
		Board createdBoard = pinterest1.createBoard(newBoard);
		List<Board> boards = pinterest1.getBoards();
		boolean boardCreated = false;
		for(Board board : boards) {
			if(board.getTitle().equals(newTitle) && board.getCategory().equals(newCategory)) {
				boardCreated = true;
				board.getCategory().getName();
				break;
			}
		}
		assertTrue("Board is not created", boardCreated);
		
		//Update
		newTitle = UUID.randomUUID().toString();
		String newDescription = UUID.randomUUID().toString();
		newCategory = BoardCategory.CARS_MOTORCYCLES;
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
	public void boardUpdateWithoutParameters() {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategory.DESIGN;
		NewBoardImpl newBoard = new NewBoardImpl(newTitle, newCategory);
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
		BoardCategory newCategory = BoardCategory.DESIGN;
		NewBoardImpl newBoard = new NewBoardImpl(newTitle, newCategory);
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
	
	@Test
	public void getBoardByURLTest() {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategory.DESIGN;
		
		//Create
		NewBoardImpl newBoard = new NewBoardImpl(newTitle, newCategory);
		Board createdBoard = pinterest1.createBoard(newBoard);
		Board createdByURLBoard = pinterest1.getBoard(createdBoard.getURL());
		assertEquals(createdBoard.getURL(), createdByURLBoard.getURL());
	}
	
	@Test
	public void followBoardTest() {
		NewBoard newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.ARCHITECTURE);
		Board board = pinterest1.createBoard(newBoard);
		assertEquals(0, board.getFollowersCount());
		Board followedBoard = pinterest2.followBoard(board);
		assertEquals(1, followedBoard.getFollowersCount());
		assertTrue("Board not followed", pinterest2.isFollowing(followedBoard));
		Board unfollowedBoard = pinterest2.unfollowBoard(followedBoard);
		assertEquals(0, unfollowedBoard.getFollowersCount());
		assertFalse("Board still followed", pinterest2.isFollowing(followedBoard));
		pinterest1.deleteBoard(board);
	}
	
	@Test(expected=PinterestRuntimeException.class)
	public void getBoarsExceptionTest() {
		pinterest1.getBoards(nonexistentUser);
	}
	
	@Test(expected=PinterestBoardNotFoundException.class)
	public void getUnexistentBoardTest() {
		pinterest1.getBoard(UUID.randomUUID().toString());
	}
	
}
