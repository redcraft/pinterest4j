package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.core.NewUserSettingsImpl;
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
		assertTrue("Is not following", pinterest2.isFollowing(pinterest1.getUser()));
		pinterest2.unfollowUser(pinterest1.getUser()); 
		assertEquals(followersCountForUser1, pinterest1.getUser().refresh().getFollowersCount());
		assertEquals(followingCountForUser2, pinterest2.getUser().refresh().getFollowingCount());
		assertFalse("Is still following", pinterest2.isFollowing(pinterest1.getUser()));
	}
}
