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

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.User;

public class PinBuilder {

	private long id;
	private String description;
	private double price;
	private String link;
	private String imageURL;
	private Board board;
	private int likesCount;
	private int repinsCount;
	private int commentsCount;
	private User pinner;
	private User originalPinner;
	private boolean repined;
	
	PinBuilder() {};
	
	public long getId() {
		return id;
	}
	public PinBuilder setId(long id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public PinBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public double getPrice() {
		return price;
	}
	public PinBuilder setPrice(double price) {
		this.price = price;
		return this;
	}
	public String getLink() {
		return link;
	}
	public PinBuilder setLink(String link) {
		this.link = link;
		return this;
	}
	public String getImageURL() {
		return imageURL;
	}
	public PinBuilder setImageURL(String imageURL) {
		this.imageURL = imageURL;
		return this;
	}
	public Board getBoard() {
		return board;
	}
	public PinBuilder setBoard(Board board) {
		this.board = board;
		return this;
	}
	
	public int getLikesCount() {
		return likesCount;
	}
	public PinBuilder setLikesCount(int likesCount) {
		this.likesCount = likesCount;
		return this;
	}
	public int getRepinsCount() {
		return repinsCount;
	}
	public PinBuilder setRepinsCount(int repinsCount) {
		this.repinsCount = repinsCount;
		return this;
	}
	public User getPinner() {
		return pinner;
	}
	public PinBuilder setPinner(User pinner) {
		this.pinner = pinner;
		return this;
	}
	public User getOriginalPinner() {
		return originalPinner;
	}
	public PinBuilder setOriginalPinner(User originalPinner) {
		this.originalPinner = originalPinner;
		return this;
	}
	public boolean isRepined() {
		return repined;
	}
	public PinBuilder setRepined(boolean repined) {
		this.repined = repined;
		return this;
	}
	
	public int getCommentsCount() {
		return commentsCount;
	}
	public PinBuilder setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
		return this;
	}
	
}
