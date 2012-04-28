package ru.redcraft.pinterest4j.core.api;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import ru.redcraft.pinterest4j.Pin;
import ru.redcraft.pinterest4j.core.NewPin;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;


public class PinAPI extends CoreAPI {
	
	private static final Logger log = Logger.getLogger(PinAPI.class);
	private static final String PIN_CREATION_ERROR = "PIN CREATION ERROR: ";
	
	public PinAPI(PinterestAccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public Pin addPinToBoard(long boardID, NewPin newPin) {
		FormDataMultiPart multipartForm = createPinAddForm(boardID, newPin);
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
		if(newPin.getImageURL() != null) {
			multipartForm.bodyPart(new FormDataBodyPart("img_url", newPin.getImageURL().toString()));
		}
		else {
			File imgFile = newPin.getImageFile();
			String[] mimeInfo = new MimetypesFileTypeMap().getContentType(imgFile).split("/");
			System.out.println(mimeInfo[0] + " " + mimeInfo[1]);
			MediaType imageType = new MediaType(mimeInfo[0], mimeInfo[1]);
			FormDataBodyPart f = new FormDataBodyPart(
		                FormDataContentDisposition.name("img").fileName(imgFile.getName()).build(),
		                imgFile, imageType);
			multipartForm.bodyPart(f);
		}
		return multipartForm;
	}

}
