package ru.redcraft.pinterest4j.core.api.components;

import ru.redcraft.pinterest4j.BoardCategory;

public class BoardBuilder {

	private long id;
	private String url;
	private String title;
	private String description;
	private BoardCategory category;
	private int pinsCount;
	private int pageCount;
	private int followersCount;
	
	public long getId() {
		return id;
	}
	public BoardBuilder setId(long id) {
		this.id = id;
		return this;
	}
	public String getURL() {
		return url;
	}
	public BoardBuilder setURL(String url) {
		this.url = url;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public BoardBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public BoardBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	public BoardCategory getCategory() {
		return category;
	}
	public BoardBuilder setCategory(BoardCategory category) {
		this.category = category;
		return this;
	}
	public int getPinsCount() {
		return pinsCount;
	}
	public BoardBuilder setPinsCount(int pinsCount) {
		this.pinsCount = pinsCount;
		return this;
	}
	public int getPageCount() {
		return pageCount;
	}
	public BoardBuilder setPageCount(int pageCount) {
		this.pageCount = pageCount;
		return this;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public BoardBuilder setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
		return this;
	}
	
}
