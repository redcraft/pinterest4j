package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.Activity;
import ru.redcraft.pinterest4j.Board;

public class CreateBoardActivity extends BaseActivity implements Activity {

	private final Board board;
	
	public CreateBoardActivity(Board board) {
		super(ActivityType.CREATE_BOARD);
		this.board = board;
	}
	
	protected CreateBoardActivity(ActivityType type, Board board) {
		super(type);
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}

}
