package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.Board;

public class FollowBoardActivity extends CreateBoardActivity {

	public FollowBoardActivity(Board board) {
		super(ActivityType.FOLLOW_BOARD, board);
	}

}
