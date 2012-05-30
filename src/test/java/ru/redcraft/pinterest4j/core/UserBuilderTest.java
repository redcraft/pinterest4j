package ru.redcraft.pinterest4j.core;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class UserBuilderTest {

	@Test
	public void test() {
		String userName = UUID.randomUUID().toString();
		String fullName = UUID.randomUUID().toString();
		String description = UUID.randomUUID().toString();
		String imageURL = UUID.randomUUID().toString();
		String twitterURL = UUID.randomUUID().toString();
		String facebookURL = UUID.randomUUID().toString();
		String siteURL = UUID.randomUUID().toString();
		String location = UUID.randomUUID().toString();
		int boardsCount = (int) Math.random() * 100;
		int pinsCount = (int) Math.random() * 100;
		int likesCount = (int) Math.random() * 100;
		int followersCount = (int) Math.random() * 100;
		int followingCount = (int) Math.random() * 100;
		
		UserBuilder builder = new UserBuilder();
		builder.setUserName(userName)
			   .setFullName(fullName)
			   .setDescription(description)
			   .setImageURL(imageURL)
			   .setTwitterURL(twitterURL)
			   .setFacebookURL(facebookURL)
			   .setSiteURL(siteURL)
			   .setLocation(location)
			   .setBoardsCount(boardsCount)
			   .setPinsCount(pinsCount)
			   .setLikesCount(likesCount)
			   .setFollowersCount(followersCount)
			   .setFollowingCount(followingCount);
		
		assertEquals(userName, builder.getUserName());
		assertEquals(fullName, builder.getFullName());
		assertEquals(description, builder.getDescription());
		assertEquals(imageURL, builder.getImageURL());
		assertEquals(twitterURL, builder.getTwitterURL());
		assertEquals(facebookURL, builder.getFacebookURL());
		assertEquals(siteURL, builder.getSiteURL());
		assertEquals(location, builder.getLocation());
		assertEquals(boardsCount, builder.getBoardsCount());
		assertEquals(pinsCount, builder.getPinsCount());
		assertEquals(likesCount, builder.getLikesCount());
		assertEquals(followersCount, builder.getFollowersCount());
		assertEquals(followingCount, builder.getFollowingCount());
		
		UserImpl user = builder.build();
		
		assertEquals(userName, user.getUserName());
		assertEquals(fullName, user.getFullName());
		assertEquals(description, user.getDescription());
		assertEquals(imageURL, user.getImageURL());
		assertEquals(twitterURL, user.getTwitterURL());
		assertEquals(facebookURL, user.getFacebookURL());
		assertEquals(siteURL, user.getSiteURL());
		assertEquals(location, user.getLocation());
		assertEquals(boardsCount, user.getBoardsCount());
		assertEquals(pinsCount, user.getPinsCount());
		assertEquals(likesCount, user.getLikesCount());
		assertEquals(followersCount, user.getFollowersCount());
		assertEquals(followingCount, user.getFollowingCount());
	}

}
