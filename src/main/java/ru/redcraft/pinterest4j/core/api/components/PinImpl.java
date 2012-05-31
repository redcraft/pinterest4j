package ru.redcraft.pinterest4j.core.api.components;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class PinImpl implements Pin {

	private final long id;
	private final String description;
	private final double price;
	private final String link;
	private final String imageURL;
	private final Board board;
	private final int likesCount;
	private final int repinsCount;
	private final int commentsCount;
	private final User pinner;
	private final User originalPinner;
	private final boolean repined;
	
	public PinImpl(long id, String description, double price, String link, String imageUrl,
			Board board, int likesCount, int repinsCount, int commentsCount, User pinner, User originalPinner,
			boolean repined) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.link = link;
		this.imageURL = imageUrl;
		this.board = board;
		this.likesCount = likesCount;
		this.repinsCount = repinsCount;
		this.commentsCount = commentsCount;
		this.pinner = pinner;
		this.originalPinner = originalPinner;
		this.repined = repined;
	}

	public Pin refresh() {
		throw new UnsupportedOperationException();
	}
	
	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}

	public String getLink() {
		return link;
	}

	public String getImageURL() {
		return imageURL;
	}

	public Board getBoard() {
		return board;
	}
	
	public int getLikesCount() {
		return likesCount;
	}

	public int getRepinsCount() {
		return repinsCount;
	}

	public User getPinner() {
		return pinner;
	}

	public User getOriginalPinner() {
		return originalPinner;
	}

	public boolean isRepined() {
		return repined;
	}
	
	public int getCommentsCount() {
		return commentsCount;
	}

	public String getURL() {
		return "/pin/" + Long.toString(id) + "/";
	}

}
