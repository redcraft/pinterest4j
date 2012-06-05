package ru.redcraft.pinterest4j.core.activities;

import ru.redcraft.pinterest4j.Activity;
import ru.redcraft.pinterest4j.Board;

public class FollowBoardActivity extends CreateBoardActivity implements Activity {

	public FollowBoardActivity(Board board) {
		super(ActivityType.FOLLOW_BOARD, board);
	}

}
