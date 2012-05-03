package ru.redcraft.pinterest4j.core;

import java.io.File;

public class NewPin {

	private final String description;
	private final double price;
	private final String link;
	private final String imageURL;
	private final File imageFile;
	
	public NewPin(String description, double price, String link, String imageURL, File imageFile) {
		super();
		this.description = description;
		this.link = link;
		this.imageURL = imageURL;
		this.imageFile = imageFile;
		this.price = price;
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

	public File getImageFile() {
		return imageFile;
	}

	@Override
	public String toString() {
		return "NewPin [description=" + description + ", price=" + price
				+ ", link=" + link + ", imageURL=" + imageURL + ", imageFile="
				+ imageFile + "]";
	}
	
}
