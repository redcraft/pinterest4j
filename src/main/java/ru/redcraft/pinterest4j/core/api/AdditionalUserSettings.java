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
