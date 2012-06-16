package ru.redcraft.pinterest4j.core.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.GiftPriceCategory;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.FollowCollection.FollowContainer;

public class PinThreads implements Iterable<Pin> {

	private final String url;
	private final InternalAPIManager apiManager;
	
	public static PinThreads createPinThreadByCategory(BoardCategory category, InternalAPIManager apiManager) {
		return new PinThreads(String.format("all/?category=%s&lazy=1", (category != null) ? category.name().toLowerCase() : "" ), apiManager);
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
