package ru.redcraft.pinterest;

import ru.redcraft.pinterest.account.Account;
import ru.redcraft.pinterest.account.AuthAccount;
import ru.redcraft.pinterest.exceptions.PinterestAuthException;
import ru.redcraft.pinterest.exceptions.PinterestUserNotFoundException;
import ru.redcraft.pinterest.interfaces.IPinterestAccount;
import ru.redcraft.pinterest.interfaces.IPinterestAuthAccount;

public abstract class Pinterest {

	public static IPinterestAccount getAccount(String login) throws PinterestUserNotFoundException {
		return new Account(login);
	}
	
	public static IPinterestAuthAccount getAccount(String login, String password) throws PinterestAuthException {
		return new AuthAccount(login, password);
	}
}
