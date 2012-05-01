package ru.redcraft.pinterest4j.core;

import java.util.HashMap;
import java.util.Map;

import ru.redcraft.pinterest4j.BoardCategory;

public class BoardCategoryImpl implements BoardCategory {

	private final String id;
	private final String name;
	
	public static final BoardCategory ARCHITECTURE = new BoardCategoryImpl("architecture", "Architecture");
	public static final BoardCategory CARS_AND_MOTORCYCLES = new BoardCategoryImpl("cars_motorcycles", "Cars & Motorcycles");
	public static final BoardCategory DESIGN = new BoardCategoryImpl("design", "Design");
	public static final BoardCategory DIY_AND_CRAFT = new BoardCategoryImpl("diy_crafts", "DIY & Craft");
	public static final BoardCategory OTHER = new BoardCategoryImpl("other", "Other");
	
	private static Map<String, BoardCategory> categories = new HashMap<String, BoardCategory>();

	static {
		categories.put(ARCHITECTURE.getId(), ARCHITECTURE);
		categories.put(CARS_AND_MOTORCYCLES.getId(), CARS_AND_MOTORCYCLES);
		categories.put(DESIGN.getId(), DESIGN);
		categories.put(DIY_AND_CRAFT.getId(), DIY_AND_CRAFT);
		categories.put(OTHER.getId(), OTHER);
	}
	
	public static BoardCategory getInstanceById(String id) {
		BoardCategory category = categories.get(id);
		return (category != null) ? category : OTHER;
	}
	
	public BoardCategoryImpl(String id, String name) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardCategoryImpl other = (BoardCategoryImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
