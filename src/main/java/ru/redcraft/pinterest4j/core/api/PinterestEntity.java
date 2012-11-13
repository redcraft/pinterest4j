/**
 * Copyright (C) 2012 Maxim Gurkin <redmax3d@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
