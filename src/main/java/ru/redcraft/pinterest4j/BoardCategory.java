package ru.redcraft.pinterest4j;


public enum BoardCategory {

	ARCHITECTURE("architecture", "Architecture"),
	CARS_MOTORCYCLES("cars_motorcycles", "Cars & Motorcycles"),
	DESIGN("design", "Design"),
	DIY_CRAFTS("diy_crafts", "DIY & Craft"),
	MEN_APPAREL("men_apparel", "Men's Apparel"),
	OTHER("other", "Other");
	
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
