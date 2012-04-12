package ru.redcraft.pinterest.board;

import ru.redcraft.pinterest.interfaces.IPinterestCategory;
import ru.redcraft.pinterest.interfaces.IPinterestNewBoard;

public class NewBoard implements IPinterestNewBoard {

	private final String title;
	private final IPinterestCategory category;
	private final BoardAccessRule accessRule;
	
	public NewBoard(String title, IPinterestCategory category) {
		this.title = title;
		this.category = category;
		this.accessRule = BoardAccessRule.ME;
	}
	
	public String getTitle() {
		return title;
	}

	public IPinterestCategory getCategory() {
		return category;
	}

	public BoardAccessRule getAccessRule() {
		return accessRule;
	}

	@Override
	public String toString() {
		return "NewBoard [title=" + title + ", category=" + category
				+ ", accessRule=" + accessRule + "]";
	}

}
