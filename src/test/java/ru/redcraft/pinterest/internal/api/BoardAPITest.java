package ru.redcraft.pinterest.internal.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardAPITest {

	@Test
	public void testCreateLink() {
		String login = "user";
		assertEquals("/user/new-board/", BoardAPI.createLink("New Board", login));
		assertEquals("/user/er-d-d-f-d-23-3/", BoardAPI.createLink("er@,d,d.F.d*23 3", login));
	}

}
