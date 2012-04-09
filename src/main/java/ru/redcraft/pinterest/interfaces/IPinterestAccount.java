package ru.redcraft.pinterest.interfaces;

import java.util.List;

public interface IPinterestAccount {

	public String getLogin();
	
	public List<IPinterestBoard> getBoards();
}
