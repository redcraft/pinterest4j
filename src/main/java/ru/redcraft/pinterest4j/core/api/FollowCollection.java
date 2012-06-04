package ru.redcraft.pinterest4j.core.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.redcraft.pinterest4j.Followable;
import ru.redcraft.pinterest4j.User;

public class FollowCollection implements Iterable<User> {

	private final Followable target;
	private final InternalAPIManager apiManager;
	private final FollowType followType;
	
	enum FollowType {
		FOLLOWERS, FOLLOWING
	}
	
	static FollowCollection getFollowersCollection(Followable followable,
			InternalAPIManager apiManager) {
		return new FollowCollection(followable, apiManager);
	}
	
	static FollowCollection getFollowingCollection(User user,
			InternalAPIManager apiManager) {
		return new FollowCollection(user, apiManager);
	}
	
	private FollowCollection(Followable followable,
			InternalAPIManager apiManager) {
		super();
		this.target = followable;
		this.apiManager = apiManager;
		this.followType = FollowType.FOLLOWERS;
	}
	
	private FollowCollection(User followable,
			InternalAPIManager apiManager) {
		super();
		this.target = followable;
		this.apiManager = apiManager;
		this.followType = FollowType.FOLLOWING;
	}

	static class FollowContainer {
		private final long marker;
		private final List<User> users;
		
		FollowContainer(long marker, List<User> users) {
			super();
			this.marker = marker;
			this.users = users;
		}

		public long getMarker() {
			return marker;
		}

		public List<User> getUsers() {
			return users;
		}

		@Override
		public String toString() {
			return "FollowContainer [marker=" + marker + ", users=" + users
					+ "]";
		}
		
	}
	
	public Iterator<User> iterator() {
		return new PinterestIterator<User>(new PineterstPaginator<User>() {

			private int page = 1;
			private long marker = 0;
			
			public List<User> getPage(int i) {
				List<User> users = null;
				if(marker != -1) {
					FollowContainer container = apiManager.getUserAPI().getFollow(target, followType, page, marker);
					marker = container.getMarker();
					users = container.getUsers();
					++ page;
				}
				else {
					users = new ArrayList<User>();
				}
				
				return users;
			}
		});
	}

}
