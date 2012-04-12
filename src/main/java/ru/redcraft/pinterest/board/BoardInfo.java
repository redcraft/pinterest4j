package ru.redcraft.pinterest.board;

import ru.redcraft.pinterest.interfaces.IPinterestAdtBoardInto;
import ru.redcraft.pinterest.interfaces.IPinterestCategory;

public class BoardInfo implements IPinterestAdtBoardInto {
	private final String title;
	private final String description;
	private final IPinterestCategory category;
	private final BoardAccessRule accessRule;
	private final int pinsCount;
	private final int pageCount;
	private final int followersCount;
	
	public static class Builder {
		
		private String title = null;
		private String description;
		private IPinterestCategory category;
		private BoardAccessRule accessRule;
		private int pinsCount;
		private int pageCount;
		private int followersCount;
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public IPinterestCategory getCategory() {
			return category;
		}

		public void setCategory(IPinterestCategory category) {
			this.category = category;
		}

		public BoardAccessRule getAccessRule() {
			return accessRule;
		}

		public void setAccessRule(BoardAccessRule accessRule) {
			this.accessRule = accessRule;
		}

		public int getPinsCount() {
			return pinsCount;
		}

		public void setPinsCount(int pinsCount) {
			this.pinsCount = pinsCount;
		}
		
		public int getPageCount() {
			return pageCount;
		}

		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}

		public int getFollowersCount() {
			return followersCount;
		}

		public void setFollowersCount(int followersCount) {
			this.followersCount = followersCount;
		}
		
		public BoardInfo build() {
			return new BoardInfo(title, description, category, accessRule, pinsCount, followersCount, pageCount);
		}
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public BoardInfo(String title, String description, IPinterestCategory category,
			BoardAccessRule accessRule, int pinsCount,
			int followersCount, int pageCount) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.accessRule = accessRule;
		this.pinsCount = pinsCount;
		this.pageCount = pageCount;
		this.followersCount = followersCount;
	}
	public IPinterestCategory getCategory() {
		return category;
	}
	public int getPinsCount() {
		return pinsCount;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public BoardAccessRule getAccessRule() {
		return accessRule;
	}

	public int getPageCount() {
		return pageCount;
	}
}
