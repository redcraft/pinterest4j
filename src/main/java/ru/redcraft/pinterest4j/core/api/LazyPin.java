package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.core.PinImpl;

public class LazyPin implements Pin {

	private final long id;
	private final PinAPI pinAPI;
	private PinImpl target = null;
	
	LazyPin(long id, PinAPI pinAPI) {
		this.id = id;
		this.pinAPI = pinAPI;
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

	@Override
	public String toString() {
		return "LazyPin [id=" + id + "]";
	}
	
}
