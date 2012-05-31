package ru.redcraft.pinterest4j;



public interface Board {

	Board refresh();
	
	long getId();
	
	String getURL();
	
	String getTitle();
	
	String getDescription();
	
	BoardCategory getCategory();
	
	int getPinsCount();
	
	int getPageCount();
	
	int getFollowersCount();
	
}
