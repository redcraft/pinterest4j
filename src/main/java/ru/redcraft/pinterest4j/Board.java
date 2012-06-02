package ru.redcraft.pinterest4j;


public interface Board extends PinsProvider, FollowersProvider, Refreshable<Board> {

	long getId();
	
	String getURL();
	
	String getTitle();
	
	String getDescription();
	
	BoardCategory getCategory();
	
	int getPageCount();
	
}
