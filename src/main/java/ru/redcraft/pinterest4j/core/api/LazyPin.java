package ru.redcraft.pinterest4j.core.api;

import java.net.URL;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.core.PinImpl;

public class LazyPin implements Pin {

	private final long id;
	private final PinAPI pinAPI;
	private final PinImpl target = null;
	
	LazyPin(long id, PinAPI pinAPI) {
		this.id = id;
		this.pinAPI = pinAPI;
	}
	
	public long getId() {
		return id;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getLink() {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getImageURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
