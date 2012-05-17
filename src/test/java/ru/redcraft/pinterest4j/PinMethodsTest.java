package ru.redcraft.pinterest4j;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ru.redcraft.pinterest4j.core.BoardCategoryImpl;
import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestPinNotFoundException;

public class PinMethodsTest extends PinterestTestBase {

	private Board board1;
	private Board board2;
	
	@Before
	public void pinTestInitialize() throws PinterestAuthException, PinterestBoardExistException {
		NewBoard newBoard = new NewBoard(UUID.randomUUID().toString(), BoardCategoryImpl.CARS_AND_MOTORCYCLES);
		board1 = pinterest1.createBoard(newBoard);
		newBoard = new NewBoard(UUID.randomUUID().toString(), BoardCategoryImpl.CARS_AND_MOTORCYCLES);
		board2 = pinterest1.createBoard(newBoard);
	}
	
	@After
	public void deletePin() {
		pinterest1.deleteBoard(board1);
		pinterest1.deleteBoard(board2);
	}
	
	@Test
	public void getPinsTest() throws PinMessageSizeException {
		int pinsCount = 3;
		String newDescription = UUID.randomUUID().toString();
		NewPin newPin = new NewPin(newDescription, 0, webLink, null, imageFile);
		for(int i = 0; i < pinsCount; ++i) {
			pinterest1.addPinToBoard(board1, newPin);
		}
		assertEquals(pinsCount, pinterest1.getPins(board1).size());
		assertEquals(0, pinterest1.getPins(board1, 2).size());
		assertTrue(pinterest1.getPins().size() >= pinsCount);
		assertTrue(pinterest1.getPins(pinterest1.getUser()).size() >= pinsCount);
	}
	
	@Test
	public void pinLifeCircleTest() throws InterruptedException, PinMessageSizeException {
		
		//Create
		
		String newDescription = UUID.randomUUID().toString();
		double newPrice = 10;
		NewPin newPin = new NewPin(newDescription, newPrice, webLink, imageLink, null);
		Pin createdPin = pinterest1.addPinToBoard(board1, newPin);
		boolean pinCreated = false;
		for(Pin pin : pinterest1.getPins(board1)) {
			if(pin.getDescription().equals(newDescription)) {
				assertEquals(newPrice, pin.getPrice(), 0);
				assertEquals(webLink, pin.getLink());
				assertEquals(board1.getId(), pin.getBoard().getId());
				pinCreated = true;
			}
		}
		assertTrue("Pin does not created", pinCreated);
		assertTrue("Image URL is not correct", createdPin.getImageURL().length() > 0);
		
		//Update
		
		newDescription = UUID.randomUUID().toString();
		newPrice = 20;
		String newLink = "http://something.com/";
		Pin updatedPin = pinterest1.updatePin(createdPin, newDescription, newPrice, newLink, board2);
		boolean pinUpdated = false;
		for(Pin pin : pinterest1.getPins(board2)) {
			if(pin.getDescription().equals(newDescription)) {
				assertEquals(newPrice, pin.getPrice(), 0);
				assertEquals(newLink, pin.getLink());
				assertEquals(board2.getId(), pin.getBoard().getId());
				pinUpdated = true;
			}
		}
		assertTrue("Pin does not updated", pinUpdated);
		
		//Delete
		
		pinterest1.deletePin(updatedPin);
		boolean pinDeleted = true;
		for(Pin pin : pinterest1.getPins(board2)) {
			if(pin.getDescription().equals(newDescription)) {
				pinDeleted = false;
			}
		}
		assertTrue("Pin does not deleted", pinDeleted);
		
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionTooLongTest() throws PinMessageSizeException {
		String newDescription = "ssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"sssssssssssssssssssssssssss";
		double newPrice = 10;
		NewPin newPin = new NewPin(newDescription, newPrice, webLink, imageLink, null);
		pinterest1.addPinToBoard(board1, newPin);
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionZeroSizeTest() throws PinMessageSizeException {
		String newDescription = "";
		double newPrice = 10;
		NewPin newPin = new NewPin(newDescription, newPrice, webLink, imageLink, null);
		pinterest1.addPinToBoard(board1, newPin);
	}
	
	@Test(expected=PinterestPinNotFoundException.class)
	public void pinNotFoundExceptoinTest() {
		pinterest1.getPinByID(13);
	}
}
