package ru.redcraft.pinterest4j;

import ru.redcraft.pinterest4j.core.PinterestImpl;
import ru.redcraft.pinterest4j.exceptions.PinterestAuthException;

public class PinterestFactory {

	public  Pinterest getInstance(String login, String password) throws PinterestAuthException {
		return new PinterestImpl(login, password);
	}
}
