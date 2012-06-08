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
	private final String name;
	
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

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
