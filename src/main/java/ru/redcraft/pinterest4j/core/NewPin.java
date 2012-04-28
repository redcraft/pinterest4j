package ru.redcraft.pinterest4j.core;

import java.io.File;
import java.net.URL;

public class NewPin {

	private final String description;
	private final URL link;
	private final URL imageURL;
	private final File imageFile;
	
	public NewPin(String description, URL link,	URL imageURL, File imageFile) {
		super();
		this.description = description;
		this.link = link;
		this.imageURL = imageURL;
		this.imageFile = imageFile;
	}

	public String getDescription() {
		return description;
	}

	public URL getLink() {
		return link;
	}

	public URL getImageURL() {
		return imageURL;
	}

	public File getImageFile() {
		return imageFile;
	}

}
