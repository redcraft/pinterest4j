package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;

public class LazyUser  implements User {
	
	private final String userName;
	private final UserAPI userAPI;
	
	LazyUser(String userName, UserAPI userAPI) {
		this.userName = userName;
		this.userAPI = userAPI;
	}
	
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "LazyUser [userName=" + userName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LazyUser other = (LazyUser) obj;
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}
	
	
}
