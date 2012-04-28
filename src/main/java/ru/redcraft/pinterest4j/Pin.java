package ru.redcraft.pinterest4j;

import java.net.URL;


public interface Pin {

	public long getId();
	
	public String getDescription();
	
	public URL getLink();
	
	public URL getImageURL();
	
	public Board getBoard();
	
}
