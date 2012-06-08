package ru.redcraft.pinterest4j;

public interface Activity {
	
	public enum ActivityType {
		PIN, REPIN, LIKE, FOLLOW_BOARD, FOLLOW_USER, CREATE_BOARD, COMMENT
	}
	
	ActivityType getActivityType();
	
	//TODO add time interval support
}
