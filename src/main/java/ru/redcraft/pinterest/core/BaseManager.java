package ru.redcraft.pinterest.core;

import ru.redcraft.pinterest.internal.api.InternalAPIManager;

public abstract class BaseManager {

	protected final InternalAPIManager apiManager; 
	
	public BaseManager(InternalAPIManager apiManager) {
		this.apiManager = apiManager;
	}
}
