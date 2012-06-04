package ru.redcraft.pinterest4j.core.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Comment;
import ru.redcraft.pinterest4j.NewPin;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestPinNotFoundException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;


public class PinAPI extends CoreAPI {
	
	private static final int MAX_PIN_DESCRIPTION_LENGTH = 500;
	
	private static final String PIN_DESCRIPTION_PROP_NAME = "og:description";
	private static final String PIN_IMAGE_PROP_NAME = "og:image";
	private static final String PIN_PINBOARD_PROP_NAME = "pinterestapp:pinboard";
	private static final String PIN_LINK_PROP_NAME = "pinterestapp:source";
	private static final String PIN_PRICE_PROP_NAME = "pinterestapp:price";
	private static final String PIN_PINNER_PROP_NAME = "pinterestapp:pinner";
	private static final String PIN_LIKES_COUNT_PROP_NAME = "pinterestapp:likes";
	private static final String PIN_REPINS_COUNT_PROP_NAME = "pinterestapp:repins";
	private static final String PIN_COMMENTS_COUNT_PROP_NAME = "pinterestapp:comments";
	private static final String PIN_ID_ATTR = "data-id";
	
	private static final Logger LOG = Logger.getLogger(PinAPI.class);
	
	private static final String PIN_CREATION_ERROR = "PIN CREATION ERROR: ";
	private static final String PIN_DELETION_ERROR = "PIN DELETION ERROR: ";
	private static final String PIN_UPDATE_ERROR = "PIN UPDATE ERROR: ";
	private static final String PIN_REPIN_ERROR = "PIN REPIN ERROR: ";
	private static final String PIN_COMMENT_ERROR = "PIN COMMENT ERROR: ";
	private static final String PIN_LIKE_ERROR = "PIN LIKE ERROR: ";
	private static final String PINS_OBTAINING_ERROR = "PIN OBTAIN ERROR: ";
	private static final String COMMENTS_OBTAINING_ERROR = "COMMENTS OBTAINING ERROR";
	
	private static final String COLLECTED_PINS = "Collected pins: ";
	
	PinAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}

	public Pin addPinToBoard(Board board, NewPin newPin) throws PinMessageSizeException {
		LOG.debug("Creating new pin on board " + board + " with new pin: " + newPin);
		int descLength = newPin.getDescription().length();
		if(descLength == 0) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_ZERO_SIZE);
		}
		if(descLength > MAX_PIN_DESCRIPTION_LENGTH) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_TOO_LONG);
		}
		board.getPinsCount(); // Magic. Pinterest boards are lazy initiated on creation
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), newPin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/create/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		Map<String, String> responseMap = parseResponse(response, PIN_CREATION_ERROR);
		LazyPin createdPin = null;
		if(!responseMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
			throw new PinterestRuntimeException(PIN_CREATION_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}
		createdPin = new LazyPin(responseMap.get("url"), getApiManager());
		LOG.debug("Pin created " + createdPin);
		return createdPin;
	}
	
	private FormDataMultiPart createPinAddForm(long boardID, NewPin newPin) {
		return createPinAddForm(boardID, newPin.getDescription(), newPin.getPrice(), newPin.getLink(), newPin.getImageURL(), newPin.getImageFile());
	}
	
	private FormDataMultiPart createPinAddForm(long boardID, String description, double price, String link, String imageUrl, File imageFile) {
		FormDataMultiPart multipartForm = new FormDataMultiPart();
		multipartForm.bodyPart(new FormDataBodyPart("board", Long.toString(boardID)));
		multipartForm.bodyPart(new FormDataBodyPart("details", description));
		multipartForm.bodyPart(new FormDataBodyPart("link", link));
		multipartForm.bodyPart(new FormDataBodyPart("csrfmiddlewaretoken", getAccessToken().getCsrfToken().getValue()));
		multipartForm.bodyPart(new FormDataBodyPart("buyable", ""));
		multipartForm.bodyPart(new FormDataBodyPart("tags", ""));
		multipartForm.bodyPart(new FormDataBodyPart("replies", ""));
		if(price != 0) {
			multipartForm.bodyPart(new FormDataBodyPart("buyable", "$" + Double.toString(price)));
		}
		if(imageUrl != null) {
			multipartForm.bodyPart(new FormDataBodyPart("img_url", imageUrl));
		}
		else {
			multipartForm.bodyPart(createImageBodyPart(imageFile));
		}
		return multipartForm;
	}

	
	private Document getPinInfoPage(long id) {
		Document doc = null;
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + Long.toString(id) + "/", false).get(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()) {
			doc = Jsoup.parse(response.getEntity(String.class));
		}
		else if(response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new PinterestPinNotFoundException(id);
		}
		else {
			throw new PinterestRuntimeException(response, PINS_OBTAINING_ERROR + BAD_SERVER_RESPONSE);
		}
		return doc;
	}
	
	public PinBuilder getCompletePin(long id) {
		LOG.debug("Getting all info for pin with id " + id);
		PinBuilder builder = new PinBuilder();
		builder.setId(id);
		Document doc = getPinInfoPage(id);
		
		Map<String, String> metaMap = new HashMap<String, String>();
		for(Element meta : doc.select("meta")) {
			metaMap.put(meta.attr("property"), meta.attr("content"));
		}
		builder.setDescription(metaMap.get(PIN_DESCRIPTION_PROP_NAME));
		builder.setImageURL(metaMap.get(PIN_IMAGE_PROP_NAME));
		builder.setLink(metaMap.get(PIN_LINK_PROP_NAME));
		builder.setPrice(Double.valueOf(metaMap.get(PIN_PRICE_PROP_NAME)));
		builder.setLikesCount(Integer.valueOf(metaMap.get(PIN_LIKES_COUNT_PROP_NAME)));
		builder.setRepinsCount(Integer.valueOf(metaMap.get(PIN_REPINS_COUNT_PROP_NAME)));
		builder.setCommentsCount(Integer.valueOf(metaMap.get(PIN_COMMENTS_COUNT_PROP_NAME)));
		builder.setBoard(new LazyBoard(metaMap.get(PIN_PINBOARD_PROP_NAME).replace(PINTEREST_URL, ""), getApiManager()));
		builder.setPinner(new LazyUser(metaMap.get(PIN_PINNER_PROP_NAME).replace(PINTEREST_URL, "").replace("/", ""), getApiManager()));
		
		Elements pinners = doc.select("p#PinnerName").first().getElementsByTag("a");
		if(pinners.size() > 1) {
			builder.setOriginalPinner(new LazyUser(pinners.get(1).attr("href").replace("/", ""), getApiManager()));
			builder.setRepined(true);
		}
		else {
			builder.setOriginalPinner(builder.getPinner());
			builder.setRepined(false);
		}
			
		return builder;
	}
	
	private List<Pin> parsePinsResponse(ClientResponse response) {
		List<Pin> pinList = new ArrayList<Pin>();
		Document doc = Jsoup.parse(response.getEntity(String.class));
		Elements htmlPins = doc.select("div.pin");
		for(Element htmlPin : htmlPins) {
			if(htmlPin.hasAttr(PinAPI.PIN_ID_ATTR)) {
				long pinID = Long.valueOf(htmlPin.attr(PinAPI.PIN_ID_ATTR));
				pinList.add(new LazyPin(pinID, getApiManager()));
			}
		}
		LOG.debug(COLLECTED_PINS + pinList.size());
		return pinList;
	}
	
	public List<Pin> getPins(User user, int page, boolean likes) {
		LOG.debug("Getting pin list for user " + user + " on page " + page + "with filter likes " + likes);
				String filter = likes ? "&filter=likes" : "";
		ClientResponse response = getWR(Protocol.HTTP, user.getUserName() + "/pins/?page=" + page + filter).get(ClientResponse.class);
		return parsePinsResponse(response);
	}
	
	public List<Pin> getPins(Board board, int page) {
		LOG.debug("Getting pin list for board " + board + " on page " + page);
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "?page=" + page).get(ClientResponse.class);
		return parsePinsResponse(response);
	}
	
	public void deletePin(Pin pin) {
		LOG.debug("Deleting pin " + pin);
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL() + "delete/").entity("{}").post(ClientResponse.class);
		if(response.getStatus() != Status.OK.getStatusCode()) {
			throw new PinterestRuntimeException(response, PIN_DELETION_ERROR + BAD_SERVER_RESPONSE);
		}
		LOG.debug("Pin deleted");
	}

	public Pin updatePin(Pin pin, String description, double price, String link, Board board) throws PinMessageSizeException {
		LOG.debug(String.format("Updating pin=%s with desc=%s, price=%f, link = %s, board=%s",
				pin, description, price, link, board));
		int descLength = description.length();
		if(descLength == 0) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_ZERO_SIZE);
		}
		if(descLength > MAX_PIN_DESCRIPTION_LENGTH) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_TOO_LONG);
		}
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), description, price, link, "", null);
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL() + "edit/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		if(response.getStatus() != Status.OK.getStatusCode()) {
			throw new PinterestRuntimeException(response, PIN_UPDATE_ERROR + BAD_SERVER_RESPONSE);
		}
		Pin updatedPin = new LazyPin(pin.getId(), getApiManager());
		LOG.debug("Pin updated");
		return updatedPin;
	}

	public Pin getPinByID(long id) {
		return new LazyPin(getCompletePin(id), getApiManager());
	}

	private Form createRepinForm(Pin pin, Board board, String description) {
		Form form = new Form();
		form.add("board", board.getId());
		form.add("id", pin.getId());
		form.add("details", description);
		form.add("csrfmiddlewaretoken", getAccessToken().getCsrfToken().getValue());
		return form;
	}
	
	public Pin repin(Pin pin, Board board, String description) {
		String newDescription = (description != null) ? description : pin.getDescription();
		LOG.debug(String.format("Repining pin = %s on board = %s with descr = %s", pin, board, newDescription));
		Form repinForm = createRepinForm(pin, board, newDescription);
		Pin repinedPin = null;
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL() + "repin/").post(ClientResponse.class, repinForm);
		Map<String, String> responseMap = parseResponse(response, PIN_REPIN_ERROR);
		if(!responseMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
			throw new PinterestRuntimeException(PIN_REPIN_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}		
		repinedPin = new LazyPin(responseMap.get("repin_url"), getApiManager());
		LOG.debug("Created repined pin=" + repinedPin);
		return repinedPin;
	}
	
	public void like(Pin pin, boolean like) {
		LOG.debug(String.format("Setting like of pin = %s to = %s", pin, like));
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL() + "like/").post(ClientResponse.class, getSwitchForm("unlike", like));
		Map<String, String> responseMap = parseResponse(response, PIN_LIKE_ERROR);
		if(!responseMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
			throw new PinterestRuntimeException(PIN_LIKE_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}
		LOG.debug("Pin like mark set to " + like);
	}

	private Form createCommentForm(Pin pin, String comment) {
		Form form = new Form();
		form.add("text", comment);
		form.add("path", "/" + pin.getURL());
		form.add("replies", "");
		return form;
	}
	
	public Comment addCommentToPin(Pin pin, String comment, User user) {
		LOG.debug(String.format("Adding comment to pin = %s with text = '%s'", pin, comment));
		Form commentForm = createCommentForm(pin, comment);
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL() + "comment/").post(ClientResponse.class, commentForm);
		Map<String, String> responseMap = parseResponse(response, PIN_COMMENT_ERROR);
		if(!responseMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
			throw new PinterestRuntimeException(PIN_COMMENT_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}
		Comment newComment = new CommentImpl(Long.valueOf(responseMap.get("id")), comment, user, new LazyPin(pin.getId(), getApiManager()));
		LOG.debug("Comment created: " + newComment);
		return newComment;
	}
	
	public void deleteComment(Comment comment) {
		LOG.debug(String.format("Deleting comment = %s", comment));
		Form commentForm = new Form();
		commentForm.add("comment", comment.getId());
		ClientResponse response = getWR(Protocol.HTTP, comment.getPin().getURL() + "deletecomment/").post(ClientResponse.class, commentForm);
		Map<String, String> responseMap = parseResponse(response, PIN_COMMENT_ERROR);
		if(!responseMap.get("status").equals("success")) {
			throw new PinterestRuntimeException(PIN_COMMENT_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}
		LOG.debug("Comment deleted");
	}

	public List<Comment> getComments(Pin pin) {
		LOG.debug("Getting comments for pin = " + pin);
		List<Comment> comments = new ArrayList<Comment>();
		ClientResponse response = getWR(Protocol.HTTP, pin.getURL()).get(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Document doc = null;
			String axajResponse = null;
			try{
				axajResponse = response.getEntity(String.class);
				doc = Jsoup.parse(new JSONObject(axajResponse).getString("footer"));
			} catch(JSONException e) {
				throw new PinterestRuntimeException(COMMENTS_OBTAINING_ERROR + axajResponse, e);
			}
			for(Element comment : doc.select("div.comment")) {
				long id = Long.valueOf(comment.getElementsByClass("DeleteComment").first().attr("data"));
				Element contentMeta = comment.getElementsByClass("CommenterMeta").first();
				User user = new LazyUser(contentMeta.getElementsByTag("a").first().attr("href").replace("/", ""), getApiManager());
				contentMeta.getElementsByTag("a").remove();
				String text = contentMeta.text();
				comments.add(new CommentImpl(id, text, user, pin));
			}
		}
		else {
			throw new PinterestRuntimeException(response, COMMENTS_OBTAINING_ERROR + BAD_SERVER_RESPONSE);
		}
		LOG.debug("Comments extracted: " + comments);
		return comments;
	}

	public boolean isLiked(Pin pin) {
		LOG.debug("Checking liked status for pin=" + pin);
		boolean liked = false;
		if(getPinInfoPage(pin.getId()).select("li.unlike-button").size() == 1) {
			liked = true;
		}
		LOG.debug("Liked state is " + liked);
		return liked;
	}

}
