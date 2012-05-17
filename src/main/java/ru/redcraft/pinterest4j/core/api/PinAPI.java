package ru.redcraft.pinterest4j.core.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.redcraft.pinterest4j.Board;
import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.core.PinBuilder;
import ru.redcraft.pinterest4j.core.PinImpl;
import ru.redcraft.pinterest4j.exceptions.PinMessageSizeException;
import ru.redcraft.pinterest4j.exceptions.PinterestPinNotFoundException;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;


public class PinAPI extends CoreAPI {
	
	private static final String PIN_DESCRIPTION_PROP_NAME = "og:description";
	private static final String PIN_IMAGE_PROP_NAME = "og:image";
	private static final String PIN_PINBOARD_PROP_NAME = "pinterestapp:pinboard";
	private static final String PIN_LINK_PROP_NAME = "pinterestapp:source";
	private static final String PIN_PRICE_PROP_NAME = "pinterestapp:price";
	
	private static final Logger log = Logger.getLogger(PinAPI.class);
	private static final String PIN_CREATION_ERROR = "PIN CREATION ERROR: ";
	private static final String PIN_DELETION_ERROR = "PIN DELETION ERROR: ";
	private static final String PIN_UPDATE_ERROR = "PIN UPDATE ERROR: ";
	private static final String PIN_REPIN_ERROR = "PIN REPIN ERROR: ";
	private static final String PIN_LIKE_ERROR = "PIN LIKE ERROR: ";
	private static final String PINS_OBTAINING_ERROR = "PIN OBTAIN ERROR: ";
	
	public PinAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}

	public Pin addPinToBoard(Board board, NewPin newPin) throws PinMessageSizeException {
		log.debug("Creating new pin on board " + board + " with new pin: " + newPin);
		int descLength = newPin.getDescription().length();
		if(descLength == 0) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_ZERO_SIZE);
		}
		if(descLength > 500) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_TOO_LONG);
		}
		board.getPinsCount(); // Magic. Pinterest boards are lazy initiated on creation
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), newPin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/create/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		Map<String, String> responseMap = parseResponse(response, PIN_CREATION_ERROR);
		LazyPin createdPin = null;
		if(responseMap.get("status").equals("failure")) {
			throw new PinterestRuntimeException(PIN_CREATION_ERROR + responseMap.get("message"));
		}
		createdPin = new LazyPin(responseMap.get("url"), this);
		log.debug("Pin created " + createdPin);
		return createdPin;
	}
	
	private FormDataMultiPart createPinAddForm(long boardID, NewPin newPin) {
		FormDataMultiPart multipartForm = new FormDataMultiPart();
		multipartForm.bodyPart(new FormDataBodyPart("board", Long.toString(boardID)));
		multipartForm.bodyPart(new FormDataBodyPart("details", newPin.getDescription()));
		multipartForm.bodyPart(new FormDataBodyPart("link", newPin.getLink().toString()));
		multipartForm.bodyPart(new FormDataBodyPart("csrfmiddlewaretoken", accessToken.getCsrfToken().getValue()));
		multipartForm.bodyPart(new FormDataBodyPart("buyable", ""));
		multipartForm.bodyPart(new FormDataBodyPart("tags", ""));
		multipartForm.bodyPart(new FormDataBodyPart("replies", ""));
		if(newPin.getPrice() != 0) {
			multipartForm.bodyPart(new FormDataBodyPart("buyable", "$" + Double.toString(newPin.getPrice())));
		}
		if(newPin.getImageURL() != null) {
			multipartForm.bodyPart(new FormDataBodyPart("img_url", newPin.getImageURL().toString()));
		}
		else {
			File imgFile = newPin.getImageFile();
			String[] mimeInfo = new MimetypesFileTypeMap().getContentType(imgFile).split("/");
			MediaType imageType = new MediaType(mimeInfo[0], mimeInfo[1]);
			FormDataBodyPart f = new FormDataBodyPart(
		                FormDataContentDisposition.name("img").fileName(imgFile.getName()).build(),
		                imgFile, imageType);
			multipartForm.bodyPart(f);
		}
		return multipartForm;
	}

	public PinImpl getCompletePin(LazyPin lazyPin) {
		log.debug("Getting all info for lazy pin " + lazyPin);
		PinBuilder builder = new PinBuilder();
		builder.setId(lazyPin.getId());
		log.debug("Getting complete info for pin with id=" + Long.toString(lazyPin.getId()));
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + Long.toString(lazyPin.getId()) + "/", false).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			Document doc = Jsoup.parse(response.getEntity(String.class));
			for(Element meta : doc.select("meta")) {
				String propName = meta.attr("property");
				String propContent = meta.attr("content");
				if(propName.equals(PIN_DESCRIPTION_PROP_NAME)) {
					builder.setDescription(propContent);
				} else if(propName.equals(PIN_IMAGE_PROP_NAME)) {
					builder.setImageURL(propContent);
				} else if(propName.equals(PIN_LINK_PROP_NAME)) {
					builder.setLink(propContent);
				} else if(propName.equals(PIN_PRICE_PROP_NAME)) {
					builder.setPrice(Double.valueOf(propContent));
				} else if(propName.equals(PIN_PINBOARD_PROP_NAME)) {
					builder.setBoard(new LazyBoard(propContent.replace("http://pinterest.com", ""), apiManager.getBoardAPI()));
				}
			}
			if(doc.select("li.unlike-button").size() == 1) {
				builder.setLiked(true);
			}
			else {
				builder.setLiked(false);
			}
		}
		else if(response.getStatus() == 404) {
			throw new PinterestPinNotFoundException(lazyPin);
		}
		else {
			throw new PinterestRuntimeException(response, PINS_OBTAINING_ERROR + "bad server response");
		}
		
		return builder.build();
	}
	
	public List<Pin> getPins(User user, int page) {
		log.debug("Getting pin list for user " + user + " on page " + page);
		List<Pin> pinList = new ArrayList<Pin>();
		ClientResponse response = getWR(Protocol.HTTP, user.getUserName() + "/pins/?page=" + page).get(ClientResponse.class);
		
		Document doc = Jsoup.parse(response.getEntity(String.class));
		Elements htmlPins = doc.select("div.pin");
		for(Element htmlPin : htmlPins) {
			if(htmlPin.hasAttr("data-id")) {
				long pinID = Long.valueOf(htmlPin.attr("data-id"));
				pinList.add(new LazyPin(pinID, this));
			}
		}
		
		log.debug("Collected pins: " + pinList.size());
		return pinList;
	}
	
	public List<Pin> getPins(User user) {
		log.debug("Getting pin list for user " + user);
		List<Pin> pinList = new ArrayList<Pin>();
		List<Pin> pinPartialList = null;
		boolean pinsLoaded = true;
		for(int i = 1; pinsLoaded; ++i) {
			pinPartialList = getPins(user, i);
			pinsLoaded = !pinPartialList.isEmpty();
			pinList.addAll(pinPartialList);
		}
		log.debug("Collected pins: " + pinList.size());
		return pinList;
	}
	
	public List<Pin> getPins(Board board, int page) {
		log.debug("Getting pin list for board " + board + " on page " + page);
		List<Pin> pinList = new ArrayList<Pin>();
		ClientResponse response = getWR(Protocol.HTTP, board.getURL() + "?page=" + page).get(ClientResponse.class);
		
		Document doc = Jsoup.parse(response.getEntity(String.class));
		Elements htmlPins = doc.select("div.pin");
		for(Element htmlPin : htmlPins) {
			if(htmlPin.hasAttr("data-id")) {
				long pinID = Long.valueOf(htmlPin.attr("data-id"));
				pinList.add(new LazyPin(pinID, this));
			}
		}
		
		log.debug("Collected pins: " + pinList.size());
		return pinList;
	}
	
	public List<Pin> getPins(Board board) {
		log.debug("Getting pin list for board " + board);
		List<Pin> pinList = new ArrayList<Pin>();
		List<Pin> pinPartialList = null;
		boolean pinsLoaded = true;
		for(int i = 1; pinsLoaded; ++i) {
			pinPartialList = getPins(board, i);
			pinsLoaded = !pinPartialList.isEmpty();
			pinList.addAll(pinPartialList);
		}
		log.debug("Collected pins: " + pinList.size());
		return pinList;
	}

	public void deletePin(Pin pin) {
		log.debug("Deleting pin " + pin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/delete/").entity("{}").post(ClientResponse.class);
		if(response.getStatus() != 200) {
			throw new PinterestRuntimeException(response, PIN_DELETION_ERROR + "bad server response");
		}
		log.debug("Pin deleted");
	}

	public Pin updatePin(Pin pin, String description, double price, String link, Board board) throws PinMessageSizeException {
		log.debug(String.format("Updating pin=%s with desc=%s, price=%f, link = %s, board=%s",
				pin, description, price, link, board));
		int descLength = description.length();
		if(descLength == 0) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_ZERO_SIZE);
		}
		if(descLength > 500) {
			throw new PinMessageSizeException(PinMessageSizeException.MSG_TOO_LONG);
		}
		NewPin newPin = new NewPin(description, price, link, "", null);
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), newPin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/edit/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		if(response.getStatus() != 200) {
			throw new PinterestRuntimeException(response, PIN_UPDATE_ERROR + "bad server response");
		}
		Pin updatedPin = new LazyPin(pin.getId(), this);
		log.debug("Pin updated");
		return updatedPin;
	}

	public Pin getPinByID(long id) {
		return getCompletePin(new LazyPin(id, this));
	}

	private Form createRepinForm(Pin pin, Board board, String description) {
		Form form = new Form();
		form.add("board", board.getId());
		form.add("id", pin.getId());
		form.add("details", description);
		form.add("csrfmiddlewaretoken", accessToken.getCsrfToken().getValue());
		return form;
	}
	
	public Pin repin(Pin pin, Board board, String description) {
		String newDescription = (description != null) ? description : pin.getDescription();
		log.debug(String.format("Repining pin = %s on board = %s with descr = %s", pin, board, newDescription));
		Form repinForm = createRepinForm(pin, board, newDescription);
		Pin repinedPin = null;
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/repin/").post(ClientResponse.class, repinForm);
		Map<String, String> responseMap = parseResponse(response, PIN_REPIN_ERROR);
		if(!responseMap.get("status").equals("success")) {
			throw new PinterestRuntimeException(PIN_REPIN_ERROR + responseMap.get("message"));
		}		
		repinedPin = new LazyPin(responseMap.get("repin_url"), this);
		log.debug("Created repined pin=" + repinedPin);
		return repinedPin;
	}
	
	public Pin like(Pin pin, boolean like) {
		log.debug(String.format("Setting like of pin = %s to = %s", pin, like));
		ClientResponse response = null;
		Form likeForm = new Form();
		likeForm.add("bla", "bla");
		if(like) {
			response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/like/").post(ClientResponse.class, likeForm);
		}
		else {
			likeForm.add("unlike", 1);
			response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/like/").post(ClientResponse.class, likeForm);
		}
		Map<String, String> responseMap = parseResponse(response, PIN_REPIN_ERROR);
		if(!responseMap.get("status").equals("success")) {
			throw new PinterestRuntimeException(PIN_LIKE_ERROR + responseMap.get("message"));
		}
		log.debug("Pin like mark set to " + like);
		return new LazyPin(pin.getId(), this);
	}

	public Pin addCommentToPin(Pin pin, String comment) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
