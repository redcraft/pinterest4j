package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class CommentImpl implements Comment {

	private final long id;
	private final String text;
	private final User user;
	private final Pin pin;
	
	public CommentImpl(long id, String text, User user, Pin pin) {
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
