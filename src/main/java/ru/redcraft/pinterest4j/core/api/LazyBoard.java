package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.core.api.components.BoardImpl;

public class LazyBoard implements Board {

	private long id = 0;
	private final String url;
	private String title;
	private String description = null;
	private BoardCategory category = null;
	private final BoardAPI boardAPI;
	private BoardImpl target = null;
	
	LazyBoard(String url, BoardAPI boardAPI) {
		this.url = url;
		this.boardAPI = boardAPI;
	}
	
	LazyBoard(BoardImpl board, BoardAPI boardAPI) {
		this.url = board.getURL();
		this.target = board;
		this.boardAPI = boardAPI;
	}
	
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
	
	LazyBoard(long id, String url, String title, String description,
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
			refresh();
		}
		return target;
	}
	
	public Board refresh() {
		target = boardAPI.getCompleteBoard(url);
		return this;
	}
	
	public long getId() {
		if(target == null && id != 0) {
			return id;
		}
		else {
			return getTarget().getId();
		}
	}

	public String getURL() {
		return url;
	}

	public String getTitle() {
		if(target == null && title != null) {
			return title;
		}
		else {
			return getTarget().getTitle();
		}
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
		return "LazyBoard [url=" + url + "]";
	}

}
