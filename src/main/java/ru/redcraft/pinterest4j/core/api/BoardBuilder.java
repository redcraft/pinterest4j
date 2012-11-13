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

import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.User;

public class BoardBuilder {

	private long id;
	private String url;
	private String title;
	private String description;
	private BoardCategory category;
	private int pinsCount;
	private int pageCount;
	private int followersCount;
	private User user;
	
	BoardBuilder() {};
	
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

	public User getUser() {
		return user;
	}
	
	public BoardBuilder setUser(User user) {
		this.user = user;
		return this;
	}
	
}
