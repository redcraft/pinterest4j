package ru.redcraft.pinterest4j.core.api;

import java.util.Iterator;
import java.util.List;

public class PinterestIterator <E> implements Iterator<E> {

	private final PineterstPaginator<E> paginator;
	
	private int currentPage = 0;
	private Iterator<E> currentIterator;
	private boolean stopUpdate = false;

	public PinterestIterator(PineterstPaginator<E> paginator) {
		super();
		this.paginator = paginator;
		loadNextPage();
	}

	private void loadNextPage() {
		if(! stopUpdate) {
			++ currentPage;
			List<E> objects = paginator.getPage(currentPage);
			currentIterator = objects.iterator();
			if(objects.size() == 0) {
				stopUpdate = true;
			}
		}
	}
	
	public boolean hasNext() {
		boolean hasNext = currentIterator.hasNext();
		if(!hasNext) {
			loadNextPage();
			hasNext = currentIterator.hasNext();
		}
		return hasNext;
	}

	public E next() {
		return currentIterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
