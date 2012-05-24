package ru.redcraft.pinterest4j;

public interface User {

	String getUserName();
	
	String getDescription();
	
	String getImageURL();
	
	String getTwitterURL();
	
	String getFacebookURL();
	
	String getSiteURL();
	
	String getLocation();
	
	int getBoardsCount();
	
	int getPinsCount();
	
	int getLikesCount();
	
	int getFollowersCount();
	
	int getFollowingCount();
	
}
