package ru.redcraft.pinterest4j.core;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.Utils;
import ru.redcraft.pinterest4j.core.api.components.BoardImpl;
import ru.redcraft.pinterest4j.core.api.components.PinBuilder;

public class PinBuilderTest {

	@Test
	public void test() {
		PinBuilder builder = new PinBuilder();
		long id = (long) Math.random() * 100;
		String description = UUID.randomUUID().toString();
		double price = Math.random() * 100;
		String link = UUID.randomUUID().toString();
		String imageURL = UUID.randomUUID().toString();
		int likesCount = (int) Math.random() * 100;
		int repinsCount = (int) Math.random() * 100;
		int commentsCount = (int) Math.random() * 100;
		User pinner = Utils.getNonexistentUser();
		User originalPinner = Utils.getNonexistentUser();
		boolean repined = false;
		Board board = new BoardImpl(0, "", "", "", BoardCategory.OTHER, 0, 0, 0);
		builder.setId(id)
			   .setDescription(description)
			   .setPrice(price)
			   .setLink(link)
			   .setImageURL(imageURL)
			   .setBoard(board)
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
		assertEquals(likesCount, builder.getLikesCount());
		assertEquals(repinsCount, builder.getRepinsCount());
		assertEquals(commentsCount, builder.getCommentsCount());
		assertEquals(pinner, builder.getPinner());
		assertEquals(originalPinner, builder.getOriginalPinner());
		assertEquals(repined, builder.isRepined());
		assertEquals("/pin/" + id + "/", builder.getURL());
		
		Pin pin = builder.build();
		assertEquals(id, pin.getId());
		assertEquals(description, pin.getDescription());
		assertEquals(price, pin.getPrice(), 0);
		assertEquals(link, pin.getLink());
		assertEquals(imageURL, pin.getImageURL());
		assertEquals(board, pin.getBoard());
		assertEquals(likesCount, pin.getLikesCount());
		assertEquals(repinsCount, pin.getRepinsCount());
		assertEquals(commentsCount, pin.getCommentsCount());
		assertEquals(pinner, pin.getPinner());
		assertEquals(originalPinner, pin.getOriginalPinner());
		assertEquals(repined, pin.isRepined());
		assertEquals("/pin/" + id + "/", pin.getURL());
	}

}
