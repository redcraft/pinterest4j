package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.components.UserImpl;

public class LazyUser  implements User {
	
	private final String userName;
	private final UserAPI userAPI;
	private UserImpl target = null;
	
	LazyUser(String userName, UserAPI userAPI) {
		this.userName = userName;
		this.userAPI = userAPI;
	}
	
	LazyUser(UserImpl user, UserAPI userAPI) {
		this.userName = user.getUserName();
		this.target = user;
		this.userAPI = userAPI;
	}
	
	private UserImpl getTarget() {
		if(target == null) {
			refresh();
		}
		return target;
	}
	
	public User refresh() {
		target = userAPI.getCompleteUser(userName);
		return this;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getFullName() {
		return getTarget().getFullName();
	}

	public String getDescription() {
		return getTarget().getDescription();
	}

	public String getImageURL() {
		return getTarget().getImageURL();
	}

	public String getTwitterURL() {
		return getTarget().getTwitterURL();
	}

	public String getFacebookURL() {
		return getTarget().getFacebookURL();
	}

	public String getSiteURL() {
		return getTarget().getSiteURL();
	}

	public String getLocation() {
		return getTarget().getLocation();
	}

	public int getBoardsCount() {
		return getTarget().getBoardsCount();
	}

	public int getPinsCount() {
		return getTarget().getPinsCount();
	}

	public int getLikesCount() {
		return getTarget().getLikesCount();
	}

	public int getFollowersCount() {
		return getTarget().getFollowersCount();
	}

	public int getFollowingCount() {
		return getTarget().getFollowingCount();
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
