package ru.redcraft.pinterest4j.core;

import java.io.File;

import ru.redcraft.pinterest4j.NewUserSettings;

public class NewUserSettingsImpl implements NewUserSettings {

	private String firstName;
	private String lastName;
	private String about;
	private String location;
	private String website;
	private File image;
	
	public String getFirstName() {
		return firstName;
	}

	public NewUserSettingsImpl setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public NewUserSettingsImpl setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getAbout() {
		return about;
	}

	public NewUserSettingsImpl setDescription(String about) {
		this.about = about;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public NewUserSettingsImpl setLocation(String location) {
		this.location = location;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public NewUserSettingsImpl setWebsite(String website) {
		this.website = website;
		return this;
	}

	public File getImage() {
		return image;
	}

	public NewUserSettingsImpl setImage(File image) {
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
