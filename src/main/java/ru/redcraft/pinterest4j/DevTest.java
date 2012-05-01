package ru.redcraft.pinterest4j;

import java.net.MalformedURLException;
import java.util.List;

import ru.redcraft.pinterest4j.exceptions.PinterestException;

public class DevTest {

	
	public static void main(String[] args ) throws PinterestException, MalformedURLException {
		Pinterest pinterest = new PinterestFactory().getInstance("redmax3d", "_tPJa4_onfyZeDL4Bm8A");
		List<Board> boards = pinterest.getBoardsForUser(pinterest.getUserForName("redmax3d"));
		for(Board board : boards) {
			System.out.println(board);
			if(!board.getTitle().equals("Red Man's Fashion")) {
				pinterest.deleteBoard(board);
			}
		}
//		IPinterestAuthAccount authAccount = PinterestOld.getAccount("redmax3d", "_tPJa4_onfyZeDL4Bm8A");
//		for(IPinterestBoard board : authAccount.getBoards()) {
//			System.out.println("Board with id = " + board.getId() + " name = " + board.getTitle());
//			IPinterestNewPin newPin = new NewPin("$10 Some Test Pin", 
//					new URL("http://redcraft.ru"),
//					null,
//					new File("/Users/RED/Downloads/tumblr_m2hnor9bsM1qfjc32o1_500.jpg"));
//			authAccount.addPinToBoard(board, newPin);
//		}
	}
	
}
