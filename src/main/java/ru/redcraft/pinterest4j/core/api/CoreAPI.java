package ru.redcraft.pinterest4j.core.api;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;

public abstract class CoreAPI {

	private final PinterestAccessToken accessToken; 
	private final InternalAPIManager apiManager;
	
	private static final String PINTEREST_DOMAIN = "pinterest.com";
	private static final String COOKIE_HEADER_NAME = "Cookie";
	
	protected static final String PINTEREST_URL = "http://pinterest.com";
	protected static final String RESPONSE_STATUS_FIELD = "status";
	protected static final String RESPONSE_MESSAGE_FIELD = "message";
	protected static final String RESPONSE_SUCCESS_STATUS = "success";
	protected static final String BAD_SERVER_RESPONSE = "bad server response";
	
	protected static final String VALUE_TAG_ATTR = "value";
	protected static final String CHECKED_TAG_ATTR = "checked";
	
	protected static final Locale PINTEREST_LOCALE = Locale.ENGLISH;
	
	public enum Protocol {HTTP, HTTPS};
	
	
	CoreAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		this.accessToken = accessToken;
		this.apiManager = apiManager;
	}
	
	protected PinterestAccessToken getAccessToken() {
		return accessToken;
	}

	protected InternalAPIManager getApiManager() {
		return apiManager;
	}

	protected WebResource.Builder getWR(Protocol protocol, String url) {
		return getWR(protocol, url, true);
	}
	
	protected WebResource.Builder getWR(Protocol protocol, String url, boolean useAJAX) {
		WebResource.Builder wr = null;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String requestURL = String.format("%s://%s/%s", protocol.name().toLowerCase(PINTEREST_LOCALE), PINTEREST_DOMAIN, url);
		wr = client.resource(UriBuilder.fromUri(requestURL).build()).getRequestBuilder();
		if(accessToken != null) {
			wr = wr.header(COOKIE_HEADER_NAME, accessToken.generateCookieHeader());
			wr = wr.header("X-CSRFToken", accessToken.getCsrfToken().getValue());
			if(useAJAX) {
				wr = wr.header("X-Requested-With", "XMLHttpRequest");
			}
		}
		return wr;
	}
	
	protected Map<String, String> parseResponse(ClientResponse response, String errorTitle) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(response.getStatus() == Status.OK.getStatusCode()) {
			try{
				JSONObject jResponse = new JSONObject(response.getEntity(String.class));
				Iterator<?> responseIterator = jResponse.keys();
				while(responseIterator.hasNext()) {
					String key = (String) responseIterator.next();
					resultMap.put(key, jResponse.getString(key));
				}
			} catch(JSONException e) {
				String msg = errorTitle + e.getMessage();
				throw new PinterestRuntimeException(response, msg, e);
			}
		} else {
			throw new PinterestRuntimeException(
					response, 
					errorTitle + BAD_SERVER_RESPONSE);
		}
		return resultMap;
	}
	
	protected FormDataBodyPart createImageBodyPart(File imgFile) {
		String[] mimeInfo = new MimetypesFileTypeMap().getContentType(imgFile).split("/");
		MediaType imageType = new MediaType(mimeInfo[0], mimeInfo[1]);
		FormDataBodyPart f = new FormDataBodyPart(
	                FormDataContentDisposition.name("img").fileName(imgFile.getName()).build(),
	                imgFile, imageType);
		return f;
	}
	
}
