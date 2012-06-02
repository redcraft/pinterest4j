package ru.redcraft.pinterest4j.core.api;

import java.util.List;

public interface PineterstPaginator <E> {
	
	List<E> getPage(int i);
	
}
