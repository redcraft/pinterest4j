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
