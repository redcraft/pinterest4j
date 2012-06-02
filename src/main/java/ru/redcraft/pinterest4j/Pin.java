package ru.redcraft.pinterest4j;

import java.util.List;

public interface Pin extends Refreshable<Pin> {

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
	
	List<Comment> getComments();
	
}
