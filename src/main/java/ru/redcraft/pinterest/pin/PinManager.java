package ru.redcraft.pinterest.pin;

import ru.redcraft.pinterest.core.BaseManager;
import ru.redcraft.pinterest.interfaces.IPinterestPinManager;
import ru.redcraft.pinterest.internal.api.InternalAPIManager;

public class PinManager extends BaseManager implements IPinterestPinManager {

	public PinManager(InternalAPIManager apiManager) {
		super(apiManager);
	}
}
