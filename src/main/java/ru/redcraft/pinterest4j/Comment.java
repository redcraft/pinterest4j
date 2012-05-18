package ru.redcraft.pinterest4j;

public interface Comment {

	long getId();
	
	String getText();
	
	User getUser();
	
	Pin getPin();
}
