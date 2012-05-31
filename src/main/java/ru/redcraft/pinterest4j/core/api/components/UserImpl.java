package ru.redcraft.pinterest4j.core.api.components;

import ru.redcraft.pinterest4j.User;

public class UserImpl implements User {

	private final String userName;
	private final String fullName;
	private final String description;
	private final String imageURL;
	private final String twitterURL;
	private final String facebookURL;
	private final String siteURL;
	private final String location;
	private final int boardsCount;
	private final int pinsCount;
	private final int likesCount;
	private final int followersCount;
	private final int followingCount;
	
	public UserImpl(String userName, String fullName, String description, String imageURL,
			String twitterURL, String facebookURL, String siteURL,
			String location, int boardsCount, int pinsCount, int likesCount,
			int followersCount, int followingCount) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.description = description;
		this.imageURL = imageURL;
		this.twitterURL = twitterURL;
		this.facebookURL = facebookURL;
		this.siteURL = siteURL;
		this.location = location;
		this.boardsCount = boardsCount;
		this.pinsCount = pinsCount;
		this.likesCount = likesCount;
		this.followersCount = followersCount;
		this.followingCount = followingCount;
	}

	public String getUserName() {
		return userName;
	}
	
	public String getFullName() {
		return fullName;
	}

	public String getDescription() {
		return description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getTwitterURL() {
		return twitterURL;
	}

	public String getFacebookURL() {
		return facebookURL;
	}

	public String getSiteURL() {
		return siteURL;
	}

	public String getLocation() {
		return location;
	}

	public int getBoardsCount() {
		return boardsCount;
	}

	public int getPinsCount() {
		return pinsCount;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}
	
}
