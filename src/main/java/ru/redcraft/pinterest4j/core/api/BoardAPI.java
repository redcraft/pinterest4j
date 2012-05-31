package ru.redcraft.pinterest4j.core.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.BoardCategory;
import ru.redcraft.pinterest4j.NewBoard;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.components.BoardBuilder;
import ru.redcraft.pinterest4j.core.api.components.BoardImpl;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardExistException;
import ru.redcraft.pinterest4j.exceptions.PinterestBoardNotFoundException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.representation.Form;

public final class BoardAPI extends CoreAPI {

	private static final String BOARD_DESCRIPTION_PROP_NAME = "og:description";
	private static final String BOARD_TITLE_PROP_NAME = "og:title";
	private static final String BOARD_CATEGORY_PROP_NAME = "pinterestapp:category";
	private static final String BOARD_PINS_PROP_NAME = "pinterestapp:pins";
	private static final String BOARD_FOLLOWERS_PROP_NAME = "pinterestapp:followers";
	
	private static final Logger LOG = Logger.getLogger(BoardAPI.class);
	private static final String BOARDS_OBTAINING_ERROR = "PINBOARDS OBTAINING ERROR: ";
	private static final String BOARD_CREATION_ERROR = "PINBOARD CREATION ERROR: ";
	private static final String BOARD_DELETION_ERROR = "PINBOARD DELETION ERROR: ";
	private static final String BOARD_UPDATE_ERROR = "PINBOARD UPDATE ERROR: ";
	
	public BoardAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}
	
	public List<Board> getBoards(User user) {
		LOG.debug("Collecting board list for user = " + user);
		List<Board> boardList = new ArrayList<Board>();
		ClientResponse response = getWR(Protocol.HTTP, user.getUserName() + "/").get(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Document doc = Jsoup.parse(response.getEntity(String.class));
			Elements htmlBoards = doc.select(".pinBoard");
			for(Element htmlBoard : htmlBoards) {
				if(htmlBoard.hasAttr("id")) {
					String stringID = htmlBoard.attr("id").replace("board", "");
					long id = Long.valueOf(stringID);
					String url = htmlBoard.select("a.link").first().attr("href");
					String name = htmlBoard.select("h3.serif").first().select("a").text();
					LazyBoard board = new LazyBoard(id, url, name, this);
					boardList.add(board);
				}
			}
		}
		else {
			throw new PinterestRuntimeException(
					response,
					BOARDS_OBTAINING_ERROR + "can't get boars list");
		}
		LOG.debug("Board count = " + boardList.size());
		return boardList;
	}
	
	public static String encodeTitle(String title) {
		return title.replace('_', ' ').replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase(PINTEREST_LOCALE);
	}
	
	public static String createLink(String title, String login) {
		return String.format("/%s/%s/", login, encodeTitle(title));
	}

	public Board createBoard(NewBoard newBoard) {
		LOG.debug("Creating board for user = " + getAccessToken().getLogin() + "using info: " + newBoard);
		LazyBoard createdBoard = null;
		Form newBoardForm = createNewBoardForm(newBoard);
		ClientResponse response = getWR(Protocol.HTTP, "board/create/").post(ClientResponse.class, newBoardForm);
		Map<String, String> responseMap = parseResponse(response, BOARD_CREATION_ERROR);
		if(responseMap.get("status").equals("failure")) {
			if(responseMap.get(RESPONSE_MESSAGE_FIELD).equals("You already have a board with that name.")) {
				throw new PinterestBoardExistException(newBoard.getTitle());
			}
			else {
				throw new PinterestRuntimeException(BOARD_CREATION_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
			}
		}
		createdBoard = new LazyBoard(Long.valueOf(responseMap.get("id")), responseMap.get("url"), responseMap.get("name"), newBoard.getCategory(), this);
		LOG.debug("Board created " + createdBoard);
		return createdBoard;
	}
	
	private Form createNewBoardForm(NewBoard newBoard) {
		Form form = new Form();
		form.add("name", newBoard.getTitle());
		form.add("category", newBoard.getCategory().getId());
		form.add("collaborator", "me");
		return form;
	}
	
	public BoardImpl getCompleteBoard(LazyBoard board) {
		LOG.debug("Getting all info for lazy board " + board);
		BoardBuilder builder = new BoardBuilder();
		builder.setURL(board.getURL());
		ClientResponse response = getWR(Protocol.HTTP, board.getURL()).get(ClientResponse.class);
		
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Document doc = Jsoup.parse(response.getEntity(String.class));
			
			Map<String, String> metaMap = new HashMap<String, String>();
			for(Element meta : doc.select("meta")) {
				metaMap.put(meta.attr("property"), meta.attr("content"));
			}
			builder.setDescription(metaMap.get(BOARD_DESCRIPTION_PROP_NAME));
			builder.setCategory(BoardCategory.valueOf(metaMap.get(BOARD_CATEGORY_PROP_NAME).toUpperCase(PINTEREST_LOCALE)));
			builder.setPinsCount(Integer.valueOf(metaMap.get(BOARD_PINS_PROP_NAME)));
			builder.setFollowersCount(Integer.valueOf(metaMap.get(BOARD_FOLLOWERS_PROP_NAME)));
			builder.setTitle(metaMap.get(BOARD_TITLE_PROP_NAME));
			
			for(Element meta : doc.select("div.BoardList").first().select("li")) {
				if(meta.child(0).text().equals(builder.getTitle())) {
					builder.setId(Long.valueOf(meta.attr("data")));
					break;
				}
			}
			builder.setPageCount(Integer.valueOf(doc.select("a.MoreGrid").first().attr("href").replace("?page=", "")) - 1);
		}
		else if(response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new PinterestBoardNotFoundException(board.getURL());
		}
		else {
			throw new PinterestRuntimeException(
					response, 
					BOARDS_OBTAINING_ERROR + BAD_SERVER_RESPONSE);
		}
		
		return builder.build();
	}

	public void deleteBoard(Board board) {
		LOG.debug("Deleting board " + board);
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "settings/").delete(ClientResponse.class);
		if(response.getStatus() != Status.OK.getStatusCode()) {
			throw new PinterestRuntimeException(
					response, 
					BOARD_DELETION_ERROR + BAD_SERVER_RESPONSE);
		}
		LOG.debug("Board deleted");
	}

	private Form createUpdateBoardForm(String title, String description, BoardCategory category) {
		Form form = new Form();
		form.add("name", title);
		form.add("description", description);
		form.add("change_BoardCollaborators", "me");
		form.add("csrfmiddlewaretoken", getAccessToken().getCsrfToken().getValue());
		form.add("collaborator_name", "Enter a name");
		form.add("collaborator_username", null);
		form.add("category", category.getId());
		return form;
	}
	
	public Board updateBoard(Board board, String title, String description, BoardCategory category) {
		LOG.debug(String.format("Updating board with uri = %s with title=%s, desc=%s, cat=%s",
				board.getURL(), title, description, category));
		Form updateBoardForm = createUpdateBoardForm(title, description, category);
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "settings/").post(ClientResponse.class, updateBoardForm);
		if(response.getStatus() != Status.OK.getStatusCode()) {
			throw new PinterestRuntimeException(
					response, 
					BOARD_UPDATE_ERROR + BAD_SERVER_RESPONSE);
		}
		Board updatedBoard = new LazyBoard(board.getId(), createLink(title, getAccessToken().getLogin()), title, description, category, this);
		LOG.debug("Board updated");
		return updatedBoard;
	}

	public Board getBoardByURL(String url) {
		return getCompleteBoard(new LazyBoard(url, this));
	}
	
}
