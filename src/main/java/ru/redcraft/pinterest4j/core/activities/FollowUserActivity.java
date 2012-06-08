package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.User;

public class FollowUserActivity extends BaseActivity {

	private final User user;
	
	public FollowUserActivity(User user) {
		super(ActivityType.FOLLOW_USER);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
