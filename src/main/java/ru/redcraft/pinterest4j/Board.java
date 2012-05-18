package ru.redcraft.pinterest4j;


public interface Board {

	long getId();
	
	String getURL();
	
	String getTitle();
	
	String getDescription();
	
	BoardCategory getCategory();
	
	int getPinsCount();
	
	int getPageCount();
	
	int getFollowersCount();
	
}
