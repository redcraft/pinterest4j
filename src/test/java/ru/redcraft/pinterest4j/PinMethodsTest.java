package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.redcraft.pinterest4j.core.NewBoardImpl;
import ru.redcraft.pinterest4j.core.NewPinImpl;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;
import ru.redcraft.pinterest4j.exceptions.PinterestPinNotFoundException;

public class PinMethodsTest extends PinterestTestBase {

	private Board board1;
	private Board board2;
	private String testDescription;
	private double testPrice = 10;
	private Pin testPin;
	
	@Before
	public void pinTestInitialize() throws PinterestAuthException {
		NewBoardImpl newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
		board1 = pinterest1.createBoard(newBoard);
		newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
		board2 = pinterest1.createBoard(newBoard);
		testDescription = UUID.randomUUID().toString();
		NewPinImpl newPin = new NewPinImpl(testDescription, testPrice, webLink, imageLink, null);
		testPin = pinterest1.addPin(board2, newPin);
	}
	
	@After
	public void deletePin() {
		pinterest1.deleteBoard(board1);
		pinterest1.deleteBoard(board2);
	}
	
	@Test
	public void getPinsTest() {
		int pinsCount = 3;
		String newDescription = UUID.randomUUID().toString();
		NewPinImpl newPin = new NewPinImpl(newDescription, 0, webLink, null, imageFile);
		for(int i = 0; i < pinsCount; ++i) {
			pinterest1.addPin(board1, newPin);
		}
		assertEquals(pinsCount, pinterest1.getPins(board1).size());
		assertEquals(0, pinterest1.getPins(board1, 2).size());
		assertTrue(pinterest1.getPins().size() >= pinsCount);
		assertTrue(pinterest1.getPins(pinterest1.getUser()).size() >= pinsCount);
	}
	
	@Test
	public void pinLifeCircleTest() throws InterruptedException {
		
		//Create
		
		String newDescription = UUID.randomUUID().toString();
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(newDescription, newPrice, webLink, imageLink, null);
		Pin createdPin = pinterest1.addPin(board1, newPin);
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
	
	@Test
	public void updatePinUsingEmptyaParamsTest() {
		Pin updatedPin = pinterest1.updatePin(testPin, null, null, null, null);
		assertEquals(testDescription, updatedPin.getDescription());
		assertEquals(testPrice, updatedPin.getPrice(), 0);
		assertEquals(webLink, updatedPin.getLink());
		assertEquals(board2.getURL(), updatedPin.getBoard().getURL());
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionTooLongTest() {
		String newDescription = "ssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"sssssssssssssssssssssssssss";
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(newDescription, newPrice, webLink, imageLink, null);
		pinterest1.addPin(board1, newPin);
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionZeroSizeTest() {
		String newDescription = "";
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(newDescription, newPrice, webLink, imageLink, null);
		pinterest1.addPin(board1, newPin);
	}
	
	@Test(expected=PinterestPinNotFoundException.class)
	public void pinNotFoundExceptoinTest() {
		pinterest1.getPin(13);
	}
	
	@Test
	public void repinTest() {
		String newDescription = UUID.randomUUID().toString();
		Pin repinedPin = pinterest1.repin(testPin, board2, newDescription);
		assertEquals(newDescription, repinedPin.getDescription());
		repinedPin = pinterest1.repin(testPin, board2, null);
		assertEquals(testDescription, repinedPin.getDescription());
	}
	
	@Test
	public void likeTest() {
		assertFalse("Pin is liked", pinterest2.isLiked(testPin));
		pinterest2.likePin(testPin);
		assertTrue("Pin is not liked", pinterest2.isLiked(testPin));
		pinterest2.unlikePin(testPin);
		assertFalse("Pin is liked", pinterest2.isLiked(testPin));
	}
	
	@Test
	public void commentTest() throws InterruptedException {
		assertEquals(0, pinterest1.getComments(testPin).size());
		
		String commentText1 = UUID.randomUUID().toString();
		String commentText2 = UUID.randomUUID().toString();
		
		Comment createdComment1 = pinterest1.addComment(testPin, commentText1);
		assertEquals(commentText1, createdComment1.getText());
		assertEquals(createdComment1.getUser(), pinterest1.getUser());
		assertEquals(1, pinterest1.getComments(testPin).size());
		
		Comment createdComment2 = pinterest2.addComment(testPin, commentText2);
		List<Comment> comments = pinterest1.getComments(testPin);
		
		assertEquals(2, pinterest1.getComments(testPin).size());
		assertEquals(commentText1, comments.get(0).getText());
		assertEquals(createdComment1.getId(), comments.get(0).getId());
		assertEquals(pinterest1.getUser().getUserName(), comments.get(0).getUser().getUserName());
		assertEquals(testPin, comments.get(0).getPin());
		
		assertEquals(commentText2, comments.get(1).getText());
		assertEquals(createdComment2.getId(), comments.get(1).getId());
		assertEquals(pinterest2.getUser().getUserName(), comments.get(1).getUser().getUserName());
		assertEquals(testPin, comments.get(1).getPin());
		
		pinterest1.deleteComment(createdComment1);
		pinterest2.deleteComment(createdComment2);
		assertEquals(0, pinterest1.getComments(testPin).size());
	}
	
	@Test
	public void pinCountersTest() {
		assertEquals(0, testPin.getLikesCount());
		assertEquals(0, testPin.getRepinsCount());
		assertEquals(0, testPin.getCommentsCount());
		assertEquals(pinterest1.getUser().getUserName(), testPin.getPinner().getUserName());
		assertEquals(pinterest1.getUser().getUserName(), testPin.getOriginalPinner().getUserName());
		assertEquals(false, testPin.isRepined());
		pinterest2.likePin(testPin);
		pinterest1.addComment(testPin, UUID.randomUUID().toString());
		pinterest2.addComment(testPin, UUID.randomUUID().toString());
		Board repinsBoard = pinterest2.createBoard(new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.ARCHITECTURE));
		Pin repinedPin = pinterest2.repin(testPin, repinsBoard, null);
		Pin refreshedPin = pinterest1.getPin(testPin.getId());
		assertEquals(1, refreshedPin.getLikesCount());
		assertEquals(1, refreshedPin.getRepinsCount());
		assertEquals(2, refreshedPin.getCommentsCount());
		assertEquals(pinterest2.getUser().getUserName(), repinedPin.getPinner().getUserName());
		assertEquals(pinterest1.getUser().getUserName(), repinedPin.getOriginalPinner().getUserName());
		assertEquals(true, repinedPin.isRepined());
		pinterest2.deleteBoard(repinsBoard);
	}
}
