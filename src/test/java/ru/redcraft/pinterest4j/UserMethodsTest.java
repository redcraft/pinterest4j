package ru.redcraft.pinterest4j;

import static org.junit.Assert.assertEquals;

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
	
	@Test(expected=PinterestUserNotFoundException.class)
	public void getUnexistentUser() {
		pinterest1.getUser(UUID.randomUUID().toString());
	}
}
