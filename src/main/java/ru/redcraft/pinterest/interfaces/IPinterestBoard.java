package ru.redcraft.pinterest.interfaces;

import java.util.List;

public interface IPinterestBoard extends IPinterestAdtBoardInto {

	public long getId();
	
	public String getURL();

	public List<IPinterestPin> getAllPins();
	
	public List<IPinterestPin> getPins(int pageNumber);
}
