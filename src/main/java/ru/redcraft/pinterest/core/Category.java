package ru.redcraft.pinterest.core;

import java.util.HashMap;
import java.util.Map;

import ru.redcraft.pinterest.interfaces.IPinterestCategory;

public class Category implements IPinterestCategory {

	private final String id;
	private final String name;
	
	public static final IPinterestCategory ARCHITECTURE = new Category("architecture", "Architecture");
	public static final IPinterestCategory CARS_AND_MOTORCYCLES = new Category("cars_motorcycles", "Cars & Motorcycles");
	public static final IPinterestCategory DESIGN = new Category("design", "Design");
	public static final IPinterestCategory DIY_AND_CRAFT = new Category("diy_crafts", "DIY & Craft");
	public static final IPinterestCategory OTHER = new Category("other", "Other");
	
	private static Map<String, IPinterestCategory> categories = new HashMap<String, IPinterestCategory>();

	static {
		categories.put(ARCHITECTURE.getId(), ARCHITECTURE);
		categories.put(CARS_AND_MOTORCYCLES.getId(), CARS_AND_MOTORCYCLES);
		categories.put(DESIGN.getId(), DESIGN);
		categories.put(DIY_AND_CRAFT.getId(), DIY_AND_CRAFT);
		categories.put(OTHER.getId(), OTHER);
	}
	
	public static IPinterestCategory getInstanceById(String id) {
		IPinterestCategory category = categories.get(id);
		return (category != null) ? category : OTHER;
	}
	
	public Category(String id, String name) {
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
