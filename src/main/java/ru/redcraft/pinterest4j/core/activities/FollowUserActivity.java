/**
 * Copyright (C) 2012 Maxim Gurkin <redmax3d@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
