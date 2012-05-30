package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.NewBoard;


public class NewBoardImpl implements NewBoard {

	private final String title;
	private final BoardCategory category;
	
	public NewBoardImpl(String title, BoardCategory category) {
		this.title = title;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}

	public BoardCategory getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "NewBoard [title=" + title + ", category=" + category + "]";
	}

}
