package ru.redcraft.pinterest4j.core.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.core.NewBoard;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public final class BoardAPI extends CoreAPI {

	private static final String BOARD_DESCRIPTION_PROP_NAME = "og:description";
	private static final String BOARD_TITLE_PROP_NAME = "og:title";
	private static final String BOARD_CATEGORY_PROP_NAME = "pinterestapp:category";
	private static final String BOARD_PINS_PROP_NAME = "pinterestapp:pins";
	private static final String BOARD_FOLLOWERS_PROP_NAME = "pinterestapp:followers";
	
	private static final Logger log = Logger.getLogger(BoardAPI.class);
	private static final String BOARD_CREATION_ERROR = "PINBOARD CREATION ERROR: ";
	private static final String BOARD_DELETION_ERROR = "PINBOARD DELETION ERROR: ";
	private static final String BOARD_UPDATE_ERROR = "PINBOARD UPDATE ERROR: ";
	
	public BoardAPI(PinterestAccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	public List<Board> getBoards(String userName) {
		log.debug("Parsing boards for token: " + accessToken);
		List<Board> boardList = new ArrayList<Board>();
		ClientResponse response = getWR(Protocol.HTTP, userName + "/").get(ClientResponse.class);
		Document doc = Jsoup.parse(response.getEntity(String.class));
		Elements htmlBoards = doc.select(".pinBoard");
		for(Element htmlBoard : htmlBoards) {
			long id = Long.valueOf(htmlBoard.attr("id").replace("board", ""));
			String url = htmlBoard.select("a.link").first().attr("href");
			String name = htmlBoard.select("h3.serif").first().select("a").text();
			LazyBoard board = new LazyBoard(id, url, name, this);
			boardList.add(board);
			log.debug("Parsed board: " + board);
		}
		return boardList;
	}
	
	public static String encodeTitle(String title) {
		return title.replace('_', ' ').replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase();
	}
	
	public static String createLink(String title, String login) {
		return String.format("/%s/%s/", login, encodeTitle(title));
	}

	public Board createBoard(NewBoard newBoard) throws PinterestBoardExistException {
		log.debug("Creating board for token: " + accessToken);
		LazyBoard createdBoard = null;
		Form newBoardForm = createNewBoardForm(newBoard);
		ClientResponse response = getWR(Protocol.HTTP, "board/create/").post(ClientResponse.class, newBoardForm);
		if(response.getStatus() == 200) {
			try{
				JSONObject jResponse = new JSONObject(response.getEntity(String.class));
				if(jResponse.getString("status").equals("failure")) {
					if(jResponse.getString("message").equals("You already have a board with that name.")) {
						throw new PinterestBoardExistException(newBoard);
					}
					else {
						throw new PinterestRuntimeException(BOARD_CREATION_ERROR + jResponse.getString("message"));
					}
				}
				createdBoard = new LazyBoard(jResponse.getLong("id"), jResponse.getString("url"), jResponse.getString("name"), this);
			} catch(JSONException e) {
				String msg = BOARD_CREATION_ERROR + e.getMessage();
				log.error(msg);
				throw new PinterestRuntimeException(msg, e);
			}
		} else {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(BOARD_CREATION_ERROR + "bad server response");
		}
		return createdBoard;
	}
	
	private Form createNewBoardForm(NewBoard newBoard) {
		Form form = new Form();
		form.add("name", newBoard.getTitle());
		form.add("category", newBoard.getCategory().getId());
		form.add("collaborator", "me");
		return form;
	}
	
//	public IPinterestAdtBoardInto getAdditionalBoardInfo(IPinterestBoard board) {
//		BoardInfo.Builder infoBuilder = BoardInfo.newBuilder();
//		ClientResponse response = getWR(Protocol.HTTP, board.getURL()).get(ClientResponse.class);
//		Document doc = Jsoup.parse(response.getEntity(String.class));
//		for(Element meta : doc.select("meta")) {
//			String propName = meta.attr("property");
//			String propContent = meta.attr("content");
//			if(propName.equals(BOARD_DESCRIPTION_PROP_NAME)) {
//				infoBuilder.setDescription(propContent);
//			} else if(propName.equals(BOARD_CATEGORY_PROP_NAME)) {
//				infoBuilder.setCategory(BoardCategoryImpl.getInstanceById(propContent));
//			} else if(propName.equals(BOARD_PINS_PROP_NAME)) {
//				infoBuilder.setPinsCount(Integer.valueOf(propContent));
//			} else if(propName.equals(BOARD_FOLLOWERS_PROP_NAME)) {
//				infoBuilder.setFollowersCount(Integer.valueOf(propContent));
//			} else if(propName.equals(BOARD_TITLE_PROP_NAME)) {
//				infoBuilder.setTitle(propContent);
//			}
//		}
//		for(Element meta : doc.select("div.BoardList").first().select("li")) {
//			if(meta.child(0).text().equals(infoBuilder.getTitle())) {
//				infoBuilder.setId(Long.valueOf(meta.attr("data")));
//				break;
//			}
//		}
//		infoBuilder.setPageCount(Integer.valueOf(doc.select("a.MoreGrid").first().attr("href").replace("?page=", "")) - 1);
//		infoBuilder.setAccessRule(BoardAccessRule.ME);
//		return infoBuilder.build();
//	}

	public void deleteBoard(Board board) {
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "settings/").delete(ClientResponse.class);
		if(response.getStatus() != 200) {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(BOARD_DELETION_ERROR + "bad server response");
		}
	}

	private Form createUpdateBoardForm(String title, String description, BoardCategory category) {
		Form form = new Form();
		form.add("name", title);
		form.add("description", description);
		form.add("change_BoardCollaborators", "me");
		form.add("csrfmiddlewaretoken", accessToken.getCsrfToken().getValue());
		form.add("collaborator_name", "Enter a name");
		form.add("collaborator_username", null);
		form.add("category", category.getId());
		return form;
	}
	
	public Board updateBoardInfo(Board board, String title, String description, BoardCategory category) {
		Form updateBoardForm = createUpdateBoardForm(title, description, category);
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "settings/").post(ClientResponse.class, updateBoardForm);
		if(response.getStatus() != 200) {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(BOARD_UPDATE_ERROR + "bad server response");
		}
		return new LazyBoard(board.getId(), createLink(title, accessToken.getLogin()), title, description, category, this);
	}
	
}
