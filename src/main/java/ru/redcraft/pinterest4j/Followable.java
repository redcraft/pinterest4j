package ru.redcraft.pinterest4j;

public interface Followable {

	String getURL();
	
	int getFollowersCount();
	
	Iterable<User> getFollowers();
	
}
