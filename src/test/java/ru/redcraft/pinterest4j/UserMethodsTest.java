package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.Activity.ActivityType;
import ru.redcraft.pinterest4j.core.NewBoardImpl;
import ru.redcraft.pinterest4j.core.NewPinImpl;
import ru.redcraft.pinterest4j.core.NewUserSettingsImpl;
import ru.redcraft.pinterest4j.core.activities.CommentActivity;
import ru.redcraft.pinterest4j.core.activities.PinActivity;
import ru.redcraft.pinterest4j.exceptions.PinterestUserNotFoundException;

public class UserMethodsTest extends PinterestTestBase {

	@Test
	public void updateUserSettingsWithEmptyValuesTest() {
		String description = UUID.randomUUID().toString();
		User user = pinterest1.getUser();
		NewUserSettingsImpl settings = new NewUserSettingsImpl();
		settings.setDescription(description);
		User newUser = pinterest1.updateUser(settings);
		assertEquals(description, newUser.getDescription());
		assertEquals(user.getFullName(), newUser.getFullName());
		assertEquals(user.getTwitterURL(), newUser.getTwitterURL());
		assertEquals(user.getFacebookURL(), newUser.getFacebookURL());
		assertEquals(user.getSiteURL(), newUser.getSiteURL());
		assertEquals(user.getLocation(), newUser.getLocation());
	}
	
	@Test
	public void updateUserSettingsTest() {
		NewUserSettingsImpl settings = new NewUserSettingsImpl();
		String firstName = UUID.randomUUID().toString();
		String lastName = UUID.randomUUID().toString();
		String description = UUID.randomUUID().toString();
		String location = UUID.randomUUID().toString();
		String website = "http://" + UUID.randomUUID().toString() + ".com";
		settings.setFirstName(firstName)
				.setLastName(lastName)
				.setDescription(description)
				.setLocation(location)
				.setWebsite(website)
				.setImage(imageFile);
		
		User newUser = pinterest1.updateUser(settings);
		assertEquals(description, newUser.getDescription());
		assertEquals(firstName + " " + lastName, newUser.getFullName());
		assertEquals(pinterest1Twitter, newUser.getTwitterURL());
		assertEquals(pinterest1Facebook, newUser.getFacebookURL());
		assertEquals(website, newUser.getSiteURL());
		assertEquals(location, newUser.getLocation());
	}
	
	@Test
	public void getUserByNameTest() {
		User user = pinterest1.getUser(id2.getLogin());
		assertEquals(pinterest2.getUser().getUserName(), user.getUserName());
		assertEquals(pinterest2.getUser().getFullName(), user.getFullName());
	}
	
	@Test(expected=PinterestUserNotFoundException.class)
	public void getUnexistentUser() {
		pinterest1.getUser(UUID.randomUUID().toString());
	}
	
	@Test
	public void followUserTest() {
		pinterest2.unfollowUser(pinterest1.getUser());
		int followersCountForUser1 = pinterest1.getUser().refresh().getFollowersCount();
		int followingCountForUser2 = pinterest2.getUser().getFollowingCount();
		pinterest2.followUser(pinterest1.getUser());
		assertEquals(followersCountForUser1 + 1, pinterest1.getUser().refresh().getFollowersCount());
		assertEquals(followingCountForUser2 + 1, pinterest2.getUser().refresh().getFollowingCount());
		assertEquals(pinterest2.getUser(), pinterest1.getUser().getFollowers().iterator().next());
		assertEquals(pinterest1.getUser(), pinterest2.getUser().getFollowing().iterator().next());
		assertTrue("Is not following", pinterest2.isFollowing(pinterest1.getUser()));
		pinterest2.unfollowUser(pinterest1.getUser()); 
		assertEquals(followersCountForUser1, pinterest1.getUser().refresh().getFollowersCount());
		assertEquals(followingCountForUser2, pinterest2.getUser().refresh().getFollowingCount());
		assertFalse("Is still following", pinterest2.isFollowing(pinterest1.getUser()));
	}
	
	@Test
	public void bordCountersTest() {
		int boardCount = pinterest1.getUser().getBoardsCount();
		int boardCountToCreate = 3;
		List<Board> createdBoard = new ArrayList<Board>();
		for(int i = 0; i < boardCountToCreate; ++i) {
			NewBoardImpl newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
			createdBoard.add(pinterest1.createBoard(newBoard));
		}
		assertEquals(boardCount + boardCountToCreate, pinterest1.getUser().refresh().getBoardsCount());
		for(Board board : createdBoard) {
			pinterest1.deleteBoard(board);
		}
		assertEquals(boardCount, pinterest1.getUser().refresh().getBoardsCount());
	}
	
	@Test
	public void pinCountersTest() {
		int userPinCount = pinterest1.getUser().getPinsCount();
		int pinCountToCreate = 3;
		NewBoardImpl newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
		Board board = pinterest1.createBoard(newBoard);
		
		String newDescription = UUID.randomUUID().toString();
		NewPinImpl newPin = new NewPinImpl(newDescription, 0, webLink, null, imageFile);
		for(int i = 0; i < pinCountToCreate; ++i) {
			pinterest1.addPin(board, newPin);
		}
		assertEquals(userPinCount + pinCountToCreate, pinterest1.getUser().refresh().getPinsCount());
		
		int page = 1;
		List<Pin> pins = pinterest1.getUser().getPins(page);
		int counter = pins.size();
		
		while(pins.size() > 0) {
			++page;
			pins = pinterest1.getUser().getPins(page);
			counter += pins.size();
		}
		assertEquals(userPinCount + pinCountToCreate, counter);
		
		counter = 0;
		for(Pin pin : pinterest1.getUser().getPins()) {
			pin.getId();
			++counter;
		}
		assertEquals(userPinCount + pinCountToCreate, counter);
		
		pinterest1.deleteBoard(board);
	}
	
	@Test
	public void likesCountersTest() {
		int userLikesCount = pinterest1.getUser().getLikesCount();
		int userRealLikesCount = 0;
		for(Pin pin : pinterest1.getUser().getLikes()) {
			pin.getId();
			++userRealLikesCount;
		}
		int pinCountToCreate = 3;
		NewBoardImpl newBoard = new NewBoardImpl(UUID.randomUUID().toString(), BoardCategory.CARS_MOTORCYCLES);
		Board board = pinterest2.createBoard(newBoard);
		
		String newDescription = UUID.randomUUID().toString();
		NewPinImpl newPin = new NewPinImpl(newDescription, 0, webLink, null, imageFile);
		for(int i = 0; i < pinCountToCreate; ++i) {
			pinterest1.likePin(pinterest2.addPin(board, newPin));
		}
		assertEquals(userLikesCount + pinCountToCreate, pinterest1.getUser().refresh().getLikesCount());
		
		int page = 1;
		List<Pin> pins = pinterest1.getUser().getLikes(page);
		int counter = pins.size();
		
		while(pins.size() > 0) {
			++page;
			pins = pinterest1.getUser().getLikes(page);
			counter += pins.size();
		}
		assertEquals(userRealLikesCount + pinCountToCreate, counter);
		
		counter = 0;
		for(Pin pin : pinterest1.getUser().getLikes()) {
			pin.getId();
			++counter;
		}
		assertEquals(userRealLikesCount + pinCountToCreate, counter);
		
		pinterest2.deleteBoard(board);
	}
	
	@Test
	public void activityTest() {
		for(Activity activity : pinterest1.getUser("mediabb").getActivity()) {
			if(activity.getActivityType() == ActivityType.COMMENT) {
				System.out.println(((CommentActivity) activity).getCommentMessage());
			}
		}
	}
}
