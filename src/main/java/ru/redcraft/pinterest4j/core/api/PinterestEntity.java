package ru.redcraft.pinterest4j.core.api;


public abstract class PinterestEntity<E, Builder> {

	private Builder target = null;
	private final InternalAPIManager apiManager;
	
	PinterestEntity(InternalAPIManager apiManager) {
		this.apiManager = apiManager;
	}
	
	public PinterestEntity(Builder target, InternalAPIManager apiManager) {
		this.apiManager = apiManager;
		this.target = target;
	}
	
	protected InternalAPIManager getApiManager() {
		return apiManager;
	}
	
	protected boolean hasTarget() {
		return target != null;
	}
	
	protected Builder getTarget() {
		if(target == null) {
			refresh();
		}
		return target;
	}
	
	protected void setTarget(Builder target) {
		this.target = target;
	}
	
	protected abstract E refresh();
}
