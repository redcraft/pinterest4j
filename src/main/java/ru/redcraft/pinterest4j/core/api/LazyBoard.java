package ru.redcraft.pinterest4j.core.api;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.core.api.components.BoardBuilder;

public class LazyBoard extends PinterestEntity<Board, BoardBuilder> implements Board {

	private long id = 0;
	private final String url;
	private String title;
	private String description = null;
	private BoardCategory category = null;
	
	LazyBoard(String url, InternalAPIManager apiManager) {
		super(apiManager);
		this.url = url;
	}
	
	LazyBoard(BoardBuilder boardBuildr, InternalAPIManager apiManager) {
		super(boardBuildr, apiManager);
		this.url = boardBuildr.getURL();
	}
	
	LazyBoard(long id, String url, String title, BoardCategory category, InternalAPIManager apiManager) {
		super(apiManager);
		this.id = id;
		this.url = url;
		this.title = title;
		this.category = category;
	}
	
	LazyBoard(long id, String url, String title, InternalAPIManager apiManager) {
		super(apiManager);
		this.id = id;
		this.url = url;
		this.title = title;
	}
	
	LazyBoard(long id, String url, String title, String description,
			BoardCategory category, InternalAPIManager apiManager) {
		super(apiManager);
		this.id = id;
		this.url = url;
		this.title = title;
		this.description = description;
		this.category = category;
	}

	public Board refresh() {
		setTarget(getApiManager().getBoardAPI().getCompleteBoard(url));
		return this;
	}
	
	public long getId() {
		if(!hasTarget() && id != 0) {
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
		if(!hasTarget() && title != null) {
			return title;
		}
		else {
			return getTarget().getTitle();
		}
	}

	public String getDescription() {
		if(!hasTarget() && description != null) {
			return description;
		}
		else {
			return getTarget().getDescription();
		}
	}

	public BoardCategory getCategory() {
		if(!hasTarget() && category != null) {
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
	
	public List<Pin> getPins(int page) {
		return getApiManager().getPinAPI().getPins(this, page);
	}

	public PinsCollection getPins() {
		return new PinsCollection(this);
	}
	
	@Override
	public String toString() {
		return "LazyBoard [url=" + url + "]";
	}

}
