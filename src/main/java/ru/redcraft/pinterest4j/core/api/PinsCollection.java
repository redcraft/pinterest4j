package ru.redcraft.pinterest4j.core.api;

import java.util.Iterator;
import java.util.List;

import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.Pinholder;

public class PinsCollection implements Iterable<Pin> {

	private final Pinholder provider;
	
	PinsCollection(Pinholder provider) {
		this.provider = provider;
	}
	
	public Iterator<Pin> iterator() {
		return new PinterestIterator<Pin>(new PineterstPaginator<Pin>() {
			public List<Pin> getPage(int page) {
				return provider.getPins(page);
			}
		});
	}

}
