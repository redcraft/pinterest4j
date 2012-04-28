package ru.redcraft.pinterest4j.core.managers;

import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public class PinManager extends BaseManager {

	public PinManager(InternalAPIManager internalAPI) {
		super(internalAPI);
	}

	public Pin addPinToBoard(long boardID, NewPin newPin) {
		Pin pin = apiManager.getPinAPI().addPinToBoard(boardID, newPin);
		return pin;
	}

}
