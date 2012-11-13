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
package ru.redcraft.pinterest4j;

public enum GiftPriceCategory {
	
	GIFTS_1_20(1, 20),
	GIFTS_20_50(20, 50),
	GIFTS_50_100(50, 100),
	GIFTS_100_200(100, 200),
	GIFTS_200_500(200, 500),
	GIFTS_500_MORE(500, 1000000);
	
	private final int from;
	private final int to;
	
	private GiftPriceCategory(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}
}
