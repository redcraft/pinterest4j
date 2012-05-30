package ru.redcraft.pinterest4j.core;

import java.io.File;

public class UserSettings {

	private String firstName;
	private String lastName;
	private String about;
	private String location;
	private String website;
	private File image;
	
	public String getFirstName() {
		return firstName;
	}

	public UserSettings setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserSettings setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getAbout() {
		return about;
	}

	public UserSettings setDescription(String about) {
		this.about = about;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public UserSettings setLocation(String location) {
		this.location = location;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public UserSettings setWebsite(String website) {
		this.website = website;
		return this;
	}

	public File getImage() {
		return image;
	}

	public UserSettings setImage(File image) {
		this.image = image;
		return this;
	}

	@Override
	public String toString() {
		return "UserSettings [firstName=" + firstName + ", lastName="
				+ lastName + ", about=" + about + ", location=" + location
				+ ", website=" + website + ", image=" + image + "]";
	}
	
}
