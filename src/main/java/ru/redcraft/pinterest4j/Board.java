package ru.redcraft.pinterest4j;


public interface Board {

	public long getId();
	
	public String getURL();
	
	public String getTitle();
	
	public String getDescription();
	
	public BoardCategory getCategory();
	
	public int getPinsCount();
	
	public int getPageCount();
	
	public int getFollowersCount();
	
}
