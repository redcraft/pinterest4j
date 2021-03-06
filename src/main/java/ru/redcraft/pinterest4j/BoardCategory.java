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


public enum BoardCategory {

	ARCHITECTURE("architecture", "Architecture"),
	ART("art", "Art"),
	CARS_MOTORCYCLES("cars_motorcycles", "Cars & Motorcycles"),
	DESIGN("design", "Design"),
	DIY_CRAFTS("diy_crafts", "DIY & Craft"),
	MEN_APPAREL("men_apparel", "Men's Apparel"),
	OTHER("other", "Other"),
	EDUCATION("education", "Education"),
	FILM_MUSIC_BOOKS("film_music_books", "Film, Music &amp; Books"),
	FITNESS("fitness", "Fitness"),
	FOOD_DRINK("food_drink", "Food &amp; Drink"),
	GARDENING("gardening", "Gardening"),
	GEEK("geek", "Geek"),
	HAIR_BEAUTY("hair_beauty", "Hair &amp; Beauty"),
	HISTORY("history", "History"),
	HOLIDAYS("holidays", "Holidays"),
	HOME("home", "Home Decor"),
	HUMOR("humor", "Humor"),
	KIDS("kids", "Kids"),
	MYLIFE("mylife", "My Life"),
	WOMEN_APPAREL("women_apparel", "Women's Apparel"),
	OUTDOORS("outdoors", "Outdoors"),
	PEOPLE("people", "People"),
	PETS("pets", "Pets"),
	PHOTOGRAPHY("photography", "Photography"),
	PRINTS_POSTERS("prints_posters", "Print &amp; Posters"),
	PRODUCTS("products", "Products"),
	SCIENCE("science", "Science &amp; Nature"),
	SPORTS("sports", "Sports"),
	TECHNOLOGY("technology", "Technology"),
	TRAVEL_PLACES("travel_places", "Travel &amp; Places"),
	WEDDING_EVENTS("wedding_events", "Wedding &amp; Events");
	                                
	private final String id;
	private String name;
	
	public static BoardCategory getInstance(String name) {
		BoardCategory category = null;
		try {
			category = BoardCategory.valueOf(name);
		} catch (IllegalArgumentException e) {
			category = BoardCategory.OTHER.setName(name);
		}
		return category;
	}
	
	private BoardCategory(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public BoardCategory setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
