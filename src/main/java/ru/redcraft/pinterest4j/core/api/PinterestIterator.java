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

public class PinterestIterator <E> implements Iterator<E> {

	private final PineterstPaginator<E> paginator;
	
	private int currentPage = 0;
	private Iterator<E> currentIterator;
	private boolean stopUpdate = false;

	PinterestIterator(PineterstPaginator<E> paginator) {
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
