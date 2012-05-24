package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.User;

public class UserBuilder implements User {

	private  String userName;
	private  String description;
	private  String imageURL;
	private  String twitterURL;
	private  String facebookURL;
	private  String siteURL;
	private  String location;
	private  int boardsCount;
	private  int pinsCount;
	private  int likesCount;
	private  int followersCount;
	private  int followingCount;
	
	public String getUserName() {
		return userName;
	}
	public UserBuilder setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public UserBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getImageURL() {
		return imageURL;
	}
	public UserBuilder setImageURL(String imageURL) {
		this.imageURL = imageURL;
		return this;
	}
	public String getTwitterURL() {
		return twitterURL;
	}
	public UserBuilder setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
		return this;
	}
	public String getFacebookURL() {
		return facebookURL;
	}
	public UserBuilder setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
		return this;
	}
	public String getSiteURL() {
		return siteURL;
	}
	public UserBuilder setSiteURL(String siteURL) {
		this.siteURL = siteURL;
		return this;
	}
	public String getLocation() {
		return location;
	}
	public UserBuilder setLocation(String location) {
		this.location = location;
		return this;
	}
	public int getBoardsCount() {
		return boardsCount;
	}
	public UserBuilder setBoardsCount(int boardsCount) {
		this.boardsCount = boardsCount;
		return this;
	}
	public int getPinsCount() {
		return pinsCount;
	}
	public UserBuilder setPinsCount(int pinsCount) {
		this.pinsCount = pinsCount;
		return this;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public UserBuilder setLikesCount(int likesCount) {
		this.likesCount = likesCount;
		return this;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public UserBuilder setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
		return this;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public UserBuilder setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
		return this;
	}
	
	public UserImpl build() {
		return new UserImpl(userName, description, imageURL, twitterURL, facebookURL, siteURL, location,
				boardsCount, pinsCount, likesCount, followersCount, followingCount);
	}
	
}
