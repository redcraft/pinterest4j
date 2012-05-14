package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;

public class BoardBuilder implements Board {

	private Long id;
	private String url;
	private String title;
	private String description;
	private BoardCategory category;
	private Integer pinsCount;
	private Integer pageCount;
	private Integer followersCount;
	
	public long getId() {
		return id;
	}
	public BoardBuilder setId(Long id) {
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
	public BoardBuilder setPinsCount(Integer pinsCount) {
		this.pinsCount = pinsCount;
		return this;
	}
	public int getPageCount() {
		return pageCount;
	}
	public BoardBuilder setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
		return this;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public BoardBuilder setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
		return this;
	}
	
	public BoardImpl build() {
		return new BoardImpl(id, url, title, description, category, pinsCount, pageCount, followersCount);
	}

}
