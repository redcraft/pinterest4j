package ru.redcraft.pinterest4j.core;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;

public class BoardBuilderTest {

	@Test
	public void test() {
		BoardBuilder builder = new BoardBuilder();
		Long id = 1234567890L;
		String url = "http://some_url";
		String title = "some_title";
		String description = "some_description";
		BoardCategory category = BoardCategoryImpl.OTHER;
		Integer pinsCount = 10;
		Integer pageCount = 2;
		Integer followersCount = 3;
		builder.setId(id)
			   .setURL(url)
			   .setTitle(title)
			   .setDescription(description)
			   .setCategory(category)
			   .setPageCount(pageCount)
			   .setPinsCount(pinsCount)
			   .setFollowersCount(followersCount);
		
		assertEquals(id, builder.getId(), 0);
		assertEquals(url, builder.getURL());
		assertEquals(title, builder.getTitle());
		assertEquals(description, builder.getDescription());
		assertEquals(category, builder.getCategory());
		assertEquals(pageCount, Integer.valueOf(builder.getPageCount()));
		assertEquals(pinsCount, Integer.valueOf(builder.getPinsCount()));
		assertEquals(followersCount, Integer.valueOf(builder.getFollowersCount()));
		
		Board board = builder.build();
		assertEquals(id, board.getId(), 0);
		assertEquals(url, board.getURL());
		assertEquals(title, board.getTitle());
		assertEquals(description, board.getDescription());
		assertEquals(category, board.getCategory());
		assertEquals(pageCount, Integer.valueOf(board.getPageCount()));
		assertEquals(pinsCount, Integer.valueOf(board.getPinsCount()));
		assertEquals(followersCount, Integer.valueOf(board.getFollowersCount()));
	}

}
