package ru.redcraft.pinterest4j;

public interface Pin {

	public long getId();
	
	public String getDescription();
	
	public double getPrice();
	
	public String getLink();
	
	public String getImageURL();
	
	public Board getBoard();
	
}
