package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Pin;

public interface AsyncPinPayload {
	void processPin(Pin pin);
	
	void processException(long id, Exception e);
}
