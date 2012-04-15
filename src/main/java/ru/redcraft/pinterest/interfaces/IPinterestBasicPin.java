package ru.redcraft.pinterest.interfaces;

import java.net.URL;

public interface IPinterestBasicPin {

	public IPinterestBoard getBoard();
	
	public String getDescription();
	
	public URL getLink();
	
	public URL getImageURL();

}
