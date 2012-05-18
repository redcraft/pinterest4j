package ru.redcraft.pinterest4j;

public interface Pin {

	long getId();
	
	String getURL();
	
	String getDescription();
	
	double getPrice();
	
	String getLink();
	
	String getImageURL();
	
	Board getBoard();
	
	boolean isLiked();
	
}
