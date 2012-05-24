package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.BoardCategory;


public class NewBoard {

	private final String title;
	private final BoardCategory category;
	
	public NewBoard(String title, BoardCategory category) {
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
