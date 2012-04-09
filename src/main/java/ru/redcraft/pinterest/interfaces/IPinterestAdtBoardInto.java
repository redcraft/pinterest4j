package ru.redcraft.pinterest.interfaces;

public interface IPinterestAdtBoardInto extends IPinterestNewBoard {

	public int getPinsCount();
	
	public int getFollowersCount();
	
	public String getDescription();
}
