package ru.redcraft.pinterest.board;

import ru.redcraft.pinterest.interfaces.IPinterestCategory;
import ru.redcraft.pinterest.interfaces.IPinterestNewBoard;

public class NewBoard implements IPinterestNewBoard {

	private final String name;
	private final IPinterestCategory category;
	private final BoardAccessRule accessRule;
	
	public NewBoard(String name, IPinterestCategory category) {
		this.name = name;
		this.category = category;
		this.accessRule = BoardAccessRule.ME;
	}
	
	public String getName() {
		return name;
	}

	public IPinterestCategory getCategory() {
		return category;
	}

	public BoardAccessRule getAccessRule() {
		return accessRule;
	}

	@Override
	public String toString() {
		return "NewBoard [name=" + name + ", category=" + category
				+ ", accessRule=" + accessRule + "]";
	}

}
