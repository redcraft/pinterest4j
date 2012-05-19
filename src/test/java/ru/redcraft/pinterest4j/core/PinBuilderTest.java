package ru.redcraft.pinterest4j.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;

public class PinBuilderTest {

	@Test
	public void test() {
		PinBuilder builder = new PinBuilder();
		long id = 123456789;
		String description = "some_description";
		double price = 10;
		String link = "http://some_link";
		String imageURL = "http://some_url";
		boolean liked = true;
		int likesCount = 23;
		int repinsCount = 43;
		int commentsCount = 55;
		User pinner = new UserImpl("pinner");
		User originalPinner = new UserImpl("original_pinner");
		boolean repined = true;
		Board board = new BoardImpl(0, "", "", "", BoardCategoryImpl.OTHER, 0, 0, 0);
		builder.setId(id)
			   .setDescription(description)
			   .setPrice(price)
			   .setLink(link)
			   .setImageURL(imageURL)
			   .setBoard(board)
			   .setLiked(liked)
			   .setLikesCount(likesCount)
			   .setRepinsCount(repinsCount)
			   .setPinner(pinner)
			   .setOriginalPinner(originalPinner)
			   .setRepined(repined)
			   .setCommentsCount(commentsCount);
		
		assertEquals(id, builder.getId());
		assertEquals(description, builder.getDescription());
		assertEquals(price, builder.getPrice(), 0);
		assertEquals(link, builder.getLink());
		assertEquals(imageURL, builder.getImageURL());
		assertEquals(board, builder.getBoard());
		assertEquals(liked, builder.isLiked());
		assertEquals(likesCount, builder.getLikesCount());
		assertEquals(repinsCount, builder.getRepinsCount());
		assertEquals(commentsCount, builder.getCommentsCount());
		assertEquals(pinner, builder.getPinner());
		assertEquals(originalPinner, builder.getOriginalPinner());
		assertEquals(repined, builder.isRepined());
		
		Pin pin = builder.build();
		assertEquals(id, pin.getId());
		assertEquals(description, pin.getDescription());
		assertEquals(price, pin.getPrice(), 0);
		assertEquals(link, pin.getLink());
		assertEquals(imageURL, pin.getImageURL());
		assertEquals(board, pin.getBoard());
		assertEquals(liked, pin.isLiked());
		assertEquals(likesCount, pin.getLikesCount());
		assertEquals(repinsCount, pin.getRepinsCount());
		assertEquals(commentsCount, pin.getCommentsCount());
		assertEquals(pinner, pin.getPinner());
		assertEquals(originalPinner, pin.getOriginalPinner());
		assertEquals(repined, pin.isRepined());
	}

}
