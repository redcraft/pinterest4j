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

import java.util.Iterator;
import java.util.List;

import ru.redcraft.pinterest4j.Activity;
import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class LazyUser extends PinterestEntity<User, UserBuilder> implements User {
	
	private final String userName;
	
	LazyUser(String userName, InternalAPIManager apiManager) {
		super(apiManager);
		this.userName = userName;
	}
	
	LazyUser(UserBuilder userBuilder, InternalAPIManager apiManager) {
		super(userBuilder, apiManager);
		this.userName = userBuilder.getUserName();
	}
	
	public User refresh() {
		setTarget(getApiManager().getUserAPI().getCompleteUser(userName));
		return this;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getURL() {
		return "/" + getUserName() + "/";
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
	
	public List<Pin> getPins(int page) {
		return getApiManager().getPinAPI().getPins(this, page, false);
	}

	public PinsCollection getPins() {
		return new PinsCollection(this);
	}
	
	public List<Pin> getLikes(int page) {
		return getApiManager().getPinAPI().getPins(this, page, true);
	}

	public Iterable<Pin> getLikes() {
		return new Iterable<Pin>() {
			public Iterator<Pin> iterator() {
				return new PinterestIterator<Pin>(new PineterstPaginator<Pin>() {
					public List<Pin> getPage(int page) {
						return getLikes(page);
					}
				});
			}
		};
	}
	
	public Iterable<User> getFollowers() {
		return FollowCollection.getFollowersCollection(this, getApiManager());
	}
	
	public Iterable<User> getFollowing() {
		return FollowCollection.getFollowingCollection(this, getApiManager());
	}
	
	public List<Board> getBoards() {
		return getApiManager().getBoardAPI().getBoards(this);
	}
	
	public List<Activity> getActivity() {
		return getApiManager().getUserAPI().getActivity(this);
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
		if (!(obj instanceof LazyUser)) {
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
