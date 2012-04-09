package ru.redcraft.pinterest.interfaces;

public interface IPinterestNewBoard {

	public enum BoardAccessRule {
		ME, PUBLIC;
	}
	
	public String getName();
	
	public IPinterestCategory getCategory();
	
	public BoardAccessRule getAccessRule();
}
