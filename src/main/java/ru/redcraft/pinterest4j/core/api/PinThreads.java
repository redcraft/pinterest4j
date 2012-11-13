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

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.GiftPriceCategory;
import ru.redcraft.pinterest4j.Pin;

public final class PinThreads implements Iterable<Pin> {

	private final String url;
	private final InternalAPIManager apiManager;
	
	public static PinThreads createPinThreadByCategory(BoardCategory category, InternalAPIManager apiManager) {
		return new PinThreads(String.format("all/?category=%s&lazy=1", (category != null) ? category.name().toLowerCase(Locale.ENGLISH) : "" ), apiManager);
	}
	
	public static PinThreads createPopularPinThread(InternalAPIManager apiManager) {
		return new PinThreads("popular/?lazy=1", apiManager);
	}
	
	public static PinThreads createGiftPinThread(GiftPriceCategory pinPriceCategory, InternalAPIManager apiManager) {
		return new PinThreads(String.format("gifts/?price_start=%d&price_end=%d&lazy=1", pinPriceCategory.getFrom(), pinPriceCategory.getTo()), apiManager);
	}
	
	private PinThreads(String url, InternalAPIManager apiManager) {
		this.url = url;
		this.apiManager = apiManager;
	}
	
	public Iterator<Pin> iterator() {
		return new PinterestIterator<Pin>(new PineterstPaginator<Pin>() {

			private int page = 1;
			private long marker = 0;
			
			public List<Pin> getPage(int i) {
				List<Pin> pins = apiManager.getPinAPI().getPinsFromThread(url, page, marker);
				marker = pins.get(0).getId();
				++ page;
				return pins;
			}
		});
	}

}
