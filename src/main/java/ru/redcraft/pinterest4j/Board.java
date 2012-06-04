package ru.redcraft.pinterest4j;


public interface Board extends Pinholder, Followable, Refreshable<Board> {

	long getId();
	
	String getTitle();
	
	String getDescription();
	
	BoardCategory getCategory();
	
	User getUser();
	
}
