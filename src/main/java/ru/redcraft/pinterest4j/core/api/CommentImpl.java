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
package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class CommentImpl implements Comment {

	private final long id;
	private final String text;
	private final User user;
	private final Pin pin;
	
	CommentImpl(long id, String text, User user, Pin pin) {
		super();
		this.id = id;
		this.text = text;
		this.user = user;
		this.pin = pin;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public Pin getPin() {
		return pin;
	}

	@Override
	public String toString() {
		return "CommentImpl [id=" + id + ", text=" + text + ", user=" + user
				+ "]";
	}


	
}
