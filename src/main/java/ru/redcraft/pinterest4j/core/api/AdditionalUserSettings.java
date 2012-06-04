package ru.redcraft.pinterest4j.core.api;

public class AdditionalUserSettings {

	private String email;
	private Gender gender;
	private String firstName;
	private String lastName;
	private String userName;
	private String website;
	private String location;
	
	AdditionalUserSettings() {};
	
	public String getEmail() {
		return email;
	}

	public AdditionalUserSettings setEmail(String email) {
		this.email = email;
		return this;
	}

	public Gender getGender() {
		return gender;
	}

	public AdditionalUserSettings setGender(Gender gender) {
		this.gender = gender;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public AdditionalUserSettings setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public AdditionalUserSettings setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public static enum Gender {
		MALE, FEMALE, UNSPECIFIED
	}

	public String getUserName() {
		return userName;
	}

	public AdditionalUserSettings setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
