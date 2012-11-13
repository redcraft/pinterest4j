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
package ru.redcraft.pinterest4j.core;

import java.io.File;

import ru.redcraft.pinterest4j.NewPin;

public class NewPinImpl implements NewPin {

	private final String description;
	private final double price;
	private final String link;
	private final String imageURL;
	private final File imageFile;
	
	public NewPinImpl(String description, double price, String link, String imageURL, File imageFile) {
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
