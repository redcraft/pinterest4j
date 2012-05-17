package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.User;

public class CommentImpl implements Comment {

	private final long id;
	private final String text;
	private final User user;
	
	public CommentImpl(long id, String text, User user) {
		super();
		this.id = id;
		this.text = text;
		this.user = user;
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

}
