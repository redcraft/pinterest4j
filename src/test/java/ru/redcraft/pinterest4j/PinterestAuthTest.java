package ru.redcraft.pinterest4j;

import org.junit.Test;

import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestAuthTest extends PinterestTestBase {

	@Test
	public void authSuccessTest() throws PinterestAuthException {
		PinterestFactory.getInstance(id1.getLogin(), id1.getPassword());
	}
	
	@Test(expected=PinterestAuthException.class)
	public void authFailerTest() throws PinterestAuthException {
		PinterestFactory.getInstance(idAutherror.getLogin(), idAutherror.getPassword());
	}
}
