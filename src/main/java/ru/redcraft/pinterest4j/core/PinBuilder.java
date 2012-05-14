package ru.redcraft.pinterest4j.core;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;

public class PinBuilder implements Pin {

	private long id;
	private String description;
	private double price;
	private String link;
	private String imageURL;
	private Board board;
	
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
	
	public PinImpl build() {
		return new PinImpl(id, description, price, link, imageURL, board);
	}

}
