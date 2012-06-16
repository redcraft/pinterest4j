package ru.redcraft.pinterest4j.core.api;

import java.util.List;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class LazyPin extends PinterestEntity<Pin, PinBuilder> implements Pin {

	private final long id;
	
	LazyPin(long id, InternalAPIManager apiManager) {
		super(apiManager);
		this.id = id;
	}
	
	LazyPin(PinBuilder pinBuilder, InternalAPIManager apiManager) {
		super(pinBuilder, apiManager);
		this.id = pinBuilder.getId();
	}
	
	LazyPin(String url, InternalAPIManager apiManager) {
		this(Long.valueOf(url.replace("/pin/", "").replace("/", "")), apiManager);
	}
	
	public Pin refresh() {
		setTarget(getApiManager().getPinAPI().getCompletePin(id));
		return this;
	}
	
	public long getId() {
		return id;
	}

	public String getDescription() {
		return getTarget().getDescription();
	}

	public String getLink() {
		return getTarget().getLink();
	}

	public String getImageURL() {
		return getTarget().getImageURL();
	}

	public Board getBoard() {
		return getTarget().getBoard();
	}

	public double getPrice() {
		return getTarget().getPrice();
	}
	
	public int getLikesCount() {
		return getTarget().getLikesCount();
	}

	public int getRepinsCount() {
		return getTarget().getRepinsCount();
	}

	public User getPinner() {
		return getTarget().getPinner();
	}

	public User getOriginalPinner() {
		return getTarget().getOriginalPinner();
	}

	public boolean isRepined() {
		return getTarget().isRepined();
	}

	public int getCommentsCount() {
		return getTarget().getCommentsCount();
	}
	
	public String getURL() {
		return "pin/" + Long.toString(id) + "/";
	}
	
	public List<Comment> getComments() {
		return getApiManager().getPinAPI().getComments(this);
	}
	
	@Override
	public String toString() {
		return "LazyPin [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int shift = 32;
		result = prime * result + (int) (id ^ (id >>> shift));
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
		if (!(obj instanceof LazyPin)) {
			return false;
		}
		LazyPin other = (LazyPin) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
