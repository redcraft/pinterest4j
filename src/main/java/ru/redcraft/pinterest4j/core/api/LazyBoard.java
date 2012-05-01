package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;

public class LazyBoard implements Board {

	private final long id;
	private final String url;
	private final String title;
	private String description = null;
	private BoardCategory category = null;
	private final BoardAPI boardAPI;
	private BoardImpl target = null;
	
	
	LazyBoard(long id, String url, String title, BoardCategory category, BoardAPI boardAPI) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.category = category;
		this.boardAPI = boardAPI;
	}
	
	LazyBoard(long id, String url, String title, BoardAPI boardAPI) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.boardAPI = boardAPI;
	}
	
	public LazyBoard(long id, String url, String title, String description,
			BoardCategory category, BoardAPI boardAPI) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.description = description;
		this.category = category;
		this.boardAPI = boardAPI;
	}

	private Board getTarget() {
		if(target == null) {
			target = boardAPI.getCompleteBoard(this);
		}
		return target;
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
		if(target == null && description != null) {
			return description;
		}
		else {
			return getTarget().getDescription();
		}
	}

	public BoardCategory getCategory() {
		if(target == null && category != null) {
			return category;
		}
		else {
			return getTarget().getCategory();
		}
	}

	public int getPinsCount() {
		return getTarget().getPinsCount();
	}

	public int getPageCount() {
		return getTarget().getPageCount();
	}

	public int getFollowersCount() {
		return getTarget().getFollowersCount();
	}

	@Override
	public String toString() {
		return "LazyBoard [id=" + id + ", url=" + url + ", title=" + title
				+ "]";
	}

}
