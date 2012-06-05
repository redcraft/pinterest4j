package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.Activity;

public class BaseActivity implements Activity {

	private final ActivityType type;
	
	public BaseActivity(ActivityType type) {
		this.type = type;
	}
	
	public ActivityType getActivityType() {
		return type;
	}

	@Override
	public String toString() {
		return "BaseActivity [type=" + type + "]";
	}
	
}
