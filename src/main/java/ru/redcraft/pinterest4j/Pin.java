package ru.redcraft.pinterest4j;

public interface Pin {

	Pin refresh();
	
	long getId();
	
	String getURL();
	
	String getDescription();
	
	double getPrice();
	
	String getLink();
	
	String getImageURL();
	
	Board getBoard();
	
	int getLikesCount();
	
	int getRepinsCount();
	
	int getCommentsCount();
	
	User getPinner();
	
	User getOriginalPinner();
	
	boolean isRepined();
	
}
