package ru.redcraft.pinterest.board;

import ru.redcraft.pinterest.interfaces.IPinterestAdtBoardInto;
import ru.redcraft.pinterest.interfaces.IPinterestBoard;
import ru.redcraft.pinterest.interfaces.IPinterestBoardManager;
import ru.redcraft.pinterest.interfaces.IPinterestCategory;

public class Board implements IPinterestBoard, IPinterestAdtBoardInto {

	private final long id;
	private final String url;
	private final String name;
	
	private IPinterestAdtBoardInto boardInfo;
	
	private IPinterestBoardManager boardManager;
	
	public Board(Long id, String url, String name) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.boardManager = null;
		this.boardInfo = null;
	}
	
	@Override
	public String toString() {
		return "Board [id=" + id + ", link=" + url + ", name=" + name + "]";
	}

	public long getId() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public String getName() {
		return name;
	}

	void setBoardManager(IPinterestBoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public IPinterestCategory getCategory() {
		loadAdtInfo();
		return boardInfo.getCategory();
	}

	public BoardAccessRule getAccessRule() {
		loadAdtInfo();
		return boardInfo.getAccessRule();
	}

	public int getPinsCount() {
		loadAdtInfo();
		return boardInfo.getPinsCount();
	}

	public int getFollowersCount() {
		loadAdtInfo();
		return boardInfo.getFollowersCount();
	}

	public String getDescription() {
		loadAdtInfo();
		return boardInfo.getDescription();
	}
	
	private void loadAdtInfo() {
		if(boardInfo == null) {
			boardInfo = boardManager.getAdditionalBoardInfo(this);
		}
	}

}
