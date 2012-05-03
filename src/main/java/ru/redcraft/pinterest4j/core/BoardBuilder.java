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
	public void setId(Long id) {
		this.id = id;
	}
	public String getURL() {
		return url;
	}
	public void setURL(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BoardCategory getCategory() {
		return category;
	}
	public void setCategory(BoardCategory category) {
		this.category = category;
	}
	public int getPinsCount() {
		return pinsCount;
	}
	public void setPinsCount(Integer pinsCount) {
		this.pinsCount = pinsCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}
	
	public BoardImpl build() {
		return new BoardImpl(id, url, title, description, category, pinsCount, pageCount, followersCount);
	}

}
