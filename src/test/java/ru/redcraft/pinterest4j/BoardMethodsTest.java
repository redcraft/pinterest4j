package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.core.NewBoardImpl;
import ru.redcraft.pinterest4j.core.NewPinImpl;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardNotFoundException;

public class BoardMethodsTest extends PinterestTestBase {
	
	@Test
	public void boardLifeCircleTest() {
		String newTitle = UUID.randomUUID().toString();
		BoardCategory newCategory = BoardCategory.DESIGN;
		
		//Create
		NewBoardImpl newBoard = new NewBoardImpl(newTitle, newCategory);
		Board createdBoard = pinterest1.createBoard(newBoard);
		List<Board> boards = pinterest1.getUser().getBoards();
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
		boards = pinterest1.getUser().getBoards();
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
		boards = pinterest1.getUser().getBoards();
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
		pinterest2.followBoard(board);
		assertEquals(1, board.refresh().getFollowersCount());
		assertEquals(pinterest2.getUser(), board.getFollowers().iterator().next());
		assertTrue("Board not followed", pinterest2.isFollowing(board));
		pinterest2.unfollowBoard(board);
		assertEquals(0, board.refresh().getFollowersCount());
		assertFalse("Board still followed", pinterest2.isFollowing(board));
		pinterest1.deleteBoard(board);
	}
	
	@Test(expected=PinterestBoardNotFoundException.class)
	public void getUnexistentBoardTest() {
		pinterest1.getBoard(UUID.randomUUID().toString());
	}
	
	@Test
	public void pinCountersTest() {
		int pinCountToCreate = 3;
		NewBoardImpl newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
		Board board = pinterest1.createBoard(newBoard);
		
		String newDescription = UUID.randomUUID().toString();
		NewPinImpl newPin = new NewPinImpl(newDescription, 0, webLink, null, imageFile);
		for(int i = 0; i < pinCountToCreate; ++i) {
			pinterest1.addPin(board, newPin);
		}
		assertEquals(pinCountToCreate, board.refresh().getPinsCount());
		
		int page = 1;
		List<Pin> pins = board.getPins(page);
		int counter = pins.size();
		
		while(pins.size() > 0) {
			++page;
			pins = board.getPins(page);
			counter += pins.size();
		}
		assertEquals(pinCountToCreate, counter);
		
		counter = 0;
		for(Pin pin : board.getPins()) {
			pin.getId();
			++counter;
		}
		assertEquals(pinCountToCreate, counter);
		
		pinterest1.deleteBoard(board);
	}
	
}
