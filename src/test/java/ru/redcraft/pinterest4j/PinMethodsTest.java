/**
 * Copyright (C) 2012 Maxim Gurkin <redmax3d@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ru.redcraft.pinterest4j.core.NewBoardImpl;
import ru.redcraft.pinterest4j.core.NewPinImpl;
import ru.redcraft.pinterest4j.core.api.AsyncPinPayload;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;
import ru.redcraft.pinterest4j.exceptions.PinterestPinNotFoundException;

public class PinMethodsTest extends PinterestTestBase {

	private Board board1;
	private Board board2;
	private String testDescription;
	private double testPrice = 10;
	private Pin testPin;
	
	private static final String LONG_DESCRIPTION = "ssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
			"sssssssssssssssssssssssssss";
	
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
	public void pinLifeCircleTest() throws InterruptedException {
		
		//Create
		
		String newDescription = UUID.randomUUID().toString();
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(newDescription, newPrice, webLink, imageLink, null);
		Pin createdPin = pinterest1.addPin(board1, newPin);
		boolean pinCreated = false;
		for(Pin pin : board1.getPins()) {
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
		for(Pin pin : board2.getPins()) {
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
		for(Pin pin : board2.getPins()) {
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
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(LONG_DESCRIPTION, newPrice, webLink, imageLink, null);
		pinterest1.addPin(board1, newPin);
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionZeroSizeTest() {
		String newDescription = "";
		double newPrice = 10;
		NewPinImpl newPin = new NewPinImpl(newDescription, newPrice, webLink, imageLink, null);
		pinterest1.addPin(board1, newPin);
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionTooLongUpdateTest() {
		pinterest1.updatePin(testPin, LONG_DESCRIPTION, null, null, null);
	}
	
	@Test(expected=PinMessageSizeException.class)
	public void pinDescriptionZeroSizeUpdateTest() {
		String newDescription = "";
		pinterest1.updatePin(testPin, newDescription, null, null, null);
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
		assertEquals(0, testPin.getComments().size());
		
		String commentText1 = UUID.randomUUID().toString();
		String commentText2 = UUID.randomUUID().toString();
		
		Comment createdComment1 = pinterest1.addComment(testPin, commentText1);
		assertEquals(commentText1, createdComment1.getText());
		assertEquals(createdComment1.getUser(), pinterest1.getUser());
		assertEquals(1, testPin.getComments().size());
		
		Comment createdComment2 = pinterest2.addComment(testPin, commentText2);
		List<Comment> comments = testPin.getComments();
		
		assertEquals(2, testPin.getComments().size());
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
		assertEquals(0, testPin.getComments().size());
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
	
	@Test
	public void getPinByIDTest() {
		Pin newPin = pinterest1.getPin(testPin.getId());
		assertEquals(testPin, newPin);
	}
	
	public void getPinsByCategoryTest() {
		int counter = 100;
		for(Pin pin : pinterest1.getPinsByCategory(BoardCategory.ARCHITECTURE)) {
			if(--counter < 0) {
				break;
			}
			System.out.println(pin + " " + pin.getPinner());
		}
	}
	
	class Red implements AsyncPinPayload {
		
		private int repins = 0;
		private int likes = 0;
		private int processed = 0;
		private int error = 0;
		
		@Override
		public void processPin(Pin pin) {
			if(pin != null) {
				System.out.println(pin.getId());
				repins += pin.getRepinsCount();
				likes += pin.getLikesCount();
			}
			else {
				System.out.println("ERROR");
			}
			++processed;
		}
		
		public int getProcessed() {
			return processed;
		}

		public int getRepins() {
			return repins;
		}

		public int getLikes() {
			return likes;
		}
		
		public int getError() {
			return error;
		}

		@Override
		public void processException(long id, Exception e) {
			++ error;
		}
		
	};
	
	@Ignore
	@Test
	public void testAsyncPins() throws InterruptedException {
		Red payload = new Red();
		int pinsCount = 0;
		long startTime = new Date().getTime();
		for(Board board : pinterest1.getUser("redmax3d").getBoards()) {
			for(Pin pin : board.getPins()) {
				pinterest1.getAsyncPin(pin.getId(), payload);
				++ pinsCount;
			}
		}
		while(pinsCount != payload.getProcessed()) {
			Thread.sleep(5000);
			System.out.println("likes: " + payload.getLikes());
			System.out.println("repins: " + payload.getRepins());
			System.out.println("processed: " + payload.getProcessed());
			System.out.println("total: " + pinsCount);
		}
		System.out.println("time (sec): " + (new Date().getTime() - startTime) / 1000);
	}
}
