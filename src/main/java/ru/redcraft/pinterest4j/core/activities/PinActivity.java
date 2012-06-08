package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.Pin;

public class PinActivity extends BaseActivity {

	private final Pin pin;
	
	public PinActivity(ActivityType type, Pin pin) {
		super(type);
		this.pin = pin;
	}
	
	public Pin getPin() {
		return pin;
	}

}
