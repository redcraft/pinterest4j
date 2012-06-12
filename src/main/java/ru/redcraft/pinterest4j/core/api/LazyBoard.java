package ru.redcraft.pinterest4j.core.api;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

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

	public int getFollowersCount() {
		return getTarget().getFollowersCount();
	}
	
	public Iterable<User> getFollowers() {
		return FollowCollection.getFollowersCollection(this, getApiManager());
	}
	
	public List<Pin> getPins(int page) {
		return getApiManager().getPinAPI().getPins(this, page);
	}

	public PinsCollection getPins() {
		return new PinsCollection(this);
	}
	
	public User getUser() {
		return getTarget().getUser();	
	}
	
	@Override
	public String toString() {
		return "LazyBoard [url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LazyBoard)) {
			return false;
		}
		LazyBoard other = (LazyBoard) obj;
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
	
}
