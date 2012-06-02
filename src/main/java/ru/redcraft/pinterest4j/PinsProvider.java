package ru.redcraft.pinterest4j;

import java.util.List;


public interface PinsProvider {

	List<Pin> getPins(int page);
	
	Iterable<Pin> getPins();
	
	int getPinsCount();
}
