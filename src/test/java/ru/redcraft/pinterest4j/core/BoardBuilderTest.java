package ru.redcraft.pinterest4j.core;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;

public class BoardBuilderTest {

	@Test
	public void test() {
		BoardBuilder builder = new BoardBuilder();
		Long id = (long) Math.random() * 100000;
		String url = UUID.randomUUID().toString();
		String title = UUID.randomUUID().toString();
		String description = UUID.randomUUID().toString();
		BoardCategory category = BoardCategory.OTHER;
		Integer pinsCount = (int) Math.random() * 100;
		Integer pageCount = (int) Math.random() * 100;
		Integer followersCount = (int) Math.random() * 100;
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
