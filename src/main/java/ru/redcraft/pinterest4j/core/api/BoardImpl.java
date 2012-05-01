package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;

public class BoardImpl implements Board {

	private final long id;
	private final String url;
	private final String title;
	private final String description;
	private final BoardCategory category;
	private final int pinsCount;
	private final int pageCount;
	private final int followersCount;
	
	public BoardImpl(long id, String url, String title, String description,
			BoardCategory category, int pinCount, int pageCount,
			int followersCount) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.description = description;
		this.category = category;
		this.pinsCount = pinCount;
		this.pageCount = pageCount;
		this.followersCount = followersCount;
	}

	public long getId() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public BoardCategory getCategory() {
		return category;
	}

	public int getPinsCount() {
		return pinsCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

}
