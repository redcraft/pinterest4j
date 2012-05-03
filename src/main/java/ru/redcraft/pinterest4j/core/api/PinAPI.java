package ru.redcraft.pinterest4j.core.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
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
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
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
	
	public PinAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}

	public Pin addPinToBoard(Board board, NewPin newPin) {
		board.getPinsCount(); // Magic. Pinterest boards are lazy initiated on creation
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), newPin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/create/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		LazyPin createdPin = null;
		if(response.getStatus() == 200) {
			try{
				JSONObject jResponse = new JSONObject(response.getEntity(String.class));
				if(jResponse.getString("status").equals("failure")) {
					throw new PinterestRuntimeException(PIN_CREATION_ERROR + jResponse.getString("message"));
				}
				long id = Long.valueOf(jResponse.getString("url").replace("/pin/", "").replace("/", ""));
				createdPin = new LazyPin(id, this);
			} catch(JSONException e) {
				String msg = PIN_CREATION_ERROR + e.getMessage();
				log.error(msg);
				log.error("RESPONSE: " + response.getEntity(String.class));
				log.error("STATUS: " + response.getStatus());
				throw new PinterestRuntimeException(msg, e);
			}
		} else {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(PIN_CREATION_ERROR + "bad server response");
		}
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
		PinBuilder builder = new PinBuilder();
		builder.setId(lazyPin.getId());
		log.debug("Getting complete info for pin with id=" + Long.toString(lazyPin.getId()));
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + Long.toString(lazyPin.getId()) + "/", false).get(ClientResponse.class);
		Document doc = Jsoup.parse(response.getEntity(String.class));
		System.out.println(response.getStatus());
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
		
		return builder.build();
	}
	
	public List<Pin> getPins(User user, int page) {
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
		return pinList;
	}
	
	public List<Pin> getPins(User user) {
		List<Pin> pinList = new ArrayList<Pin>();
		List<Pin> pinPartialList = null;
		boolean pinsLoaded = true;
		for(int i = 1; pinsLoaded; ++i) {
			pinPartialList = getPins(user, i);
			pinsLoaded = !pinPartialList.isEmpty();
			pinList.addAll(pinPartialList);
		}
		return pinList;
	}
	
	public List<Pin> getPins(Board board, int page) {
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
		return pinList;
	}
	
	public List<Pin> getPins(Board board) {
		List<Pin> pinList = new ArrayList<Pin>();
		List<Pin> pinPartialList = null;
		boolean pinsLoaded = true;
		for(int i = 1; pinsLoaded; ++i) {
			pinPartialList = getPins(board, i);
			pinsLoaded = !pinPartialList.isEmpty();
			pinList.addAll(pinPartialList);
		}
		return pinList;
	}

	public void deletePin(Pin pin) {
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/delete/").entity("{}").post(ClientResponse.class);
		if(response.getStatus() != 200) {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(PIN_DELETION_ERROR + "bad server response");
		}
	}

	public Pin updatePin(Pin pin, String description, double price, String link, Board board) {
		NewPin newPin = new NewPin(description, price, link, "", null);
		FormDataMultiPart multipartForm = createPinAddForm(board.getId(), newPin);
		ClientResponse response = getWR(Protocol.HTTP, "pin/" + pin.getId() + "/edit/").
				type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, multipartForm);
		if(response.getStatus() != 200) {
			log.error("ERROR status: " + response.getStatus());
			log.error("ERROR message: " + response.getEntity(String.class));
			throw new PinterestRuntimeException(PIN_UPDATE_ERROR + "bad server response");
		}
		return new LazyPin(pin.getId(), this);
	}
	

}
