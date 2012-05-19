package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.PinImpl;

public class LazyPin implements Pin {

	private final long id;
	private final PinAPI pinAPI;
	private PinImpl target = null;
	
	LazyPin(long id, PinAPI pinAPI) {
		this.id = id;
		this.pinAPI = pinAPI;
	}
	
	LazyPin(String url, PinAPI pinAPI) {
		this(Long.valueOf(url.replace("/pin/", "").replace("/", "")), pinAPI);
	}
	
	private PinImpl getTarget() {
		if(target == null) {
			target = pinAPI.getCompletePin(this);
		}
		return target;
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
	
	public boolean isLiked() {
		return getTarget().isLiked();
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
	
	@Override
	public String toString() {
		return "LazyPin [id=" + id + "]";
	}

}
