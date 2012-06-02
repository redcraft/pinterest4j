package ru.redcraft.pinterest4j;

import java.util.List;


public interface User extends PinsProvider, FollowersProvider, Refreshable<User> {

	String getUserName();
	
	String getFullName();
	
	String getDescription();
	
	String getImageURL();
	
	String getTwitterURL();
	
	String getFacebookURL();
	
	String getSiteURL();
	
	String getLocation();
	
	int getBoardsCount();
	
	int getLikesCount();
	
	List<Pin> getLikes(int page);
	
	Iterable<Pin> getLikes();
	
	List<Board> getBoards();
	
	int getFollowingCount();
}
