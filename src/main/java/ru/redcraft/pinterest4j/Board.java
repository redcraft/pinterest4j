package ru.redcraft.pinterest4j;

import java.util.List;

public interface Board {

	public long getId();
	
	public String getURL();
	
	public String getTitle();
	
	public String getDescription();
	
	public BoardCategory getCategory();
	
	public int getPinsCount();
	
	public int getPageCount();
	
	public int getFollowersCount();
	
	public List<Pin> getAllPins();
	
	public List<Pin> getPins(int pageNumber);
}
