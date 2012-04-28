package ru.redcraft.pinterest4j.core.managers;

import ru.redcraft.pinterest4j.core.api.InternalAPIManager;

public abstract class BaseManager {

	protected final InternalAPIManager apiManager; 
	
	public BaseManager(InternalAPIManager apiManager) {
		this.apiManager = apiManager;
	}
}
