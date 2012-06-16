package ru.redcraft.pinterest4j.core.api;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;

public abstract class CoreAPI {

	private final PinterestAccessToken accessToken; 
	private final InternalAPIManager apiManager;
	
	private static final String PINTEREST_DOMAIN = "pinterest.com";
	private static final String COOKIE_HEADER_NAME = "Cookie";
	
	protected static final String PINTEREST_URL = "http://" + PINTEREST_DOMAIN;
	protected static final String RESPONSE_STATUS_FIELD = "status";
	protected static final String RESPONSE_MESSAGE_FIELD = "message";
	protected static final String RESPONSE_SUCCESS_STATUS = "success";
	protected static final String BAD_SERVER_RESPONSE = " bad server response";
	
	protected static final String VALUE_TAG_ATTR = "value";
	protected static final String CHECKED_TAG_ATTR = "checked";
	protected static final String HREF_TAG_ATTR = "href";
	protected static final String DATA_ID_TAG_ATTR = "data-id";
	
	protected static final Locale PINTEREST_LOCALE = Locale.ENGLISH;
	
	enum Protocol {HTTP, HTTPS};
	
	enum Method {GET, POST, DELETE};
	
	private static final Class<ClientResponse> RESPONSE_CLASS = ClientResponse.class;

	class APIRequestBuilder {
		private Protocol protocol = Protocol.HTTP;
		private Method method = Method.GET;
		private MediaType mediaType = null;
		private Object requestEntity = null;
		private final String url;
		private boolean ajaxUsage = true;
		private Map<Status, PinterestRuntimeException> exceptionMap = new HashMap<Status, PinterestRuntimeException>();
		private Status httpSuccessStatus = Status.OK;
		private String errorMessage = null;
		
		class APIResponse {
			private final ClientResponse response;
			
			APIResponse(ClientResponse response) {
				this.response = response;
			}
			
			public ClientResponse getResponse() {
				return response;
			}
			
			public Document getDocument() {
				String entety = response.getEntity(String.class);
				return Jsoup.parse(entety);	
			}
			
			public Map<String, String> parseResponse() {
				return parseResponse(null, null);
			}
			
			public Map<String, String> parseResponse(String errorMsg, PinterestRuntimeException exception) {
				Map<String, String> resultMap = new HashMap<String, String>();
				try{
					JSONObject jResponse = new JSONObject(response.getEntity(String.class));
					Iterator<?> responseIterator = jResponse.keys();
					while(responseIterator.hasNext()) {
						String key = (String) responseIterator.next();
						resultMap.put(key, jResponse.getString(key));
					}
				} catch(JSONException e) {
					String msg = errorMessage + e.getMessage();
					throw new PinterestRuntimeException(response, msg, e);
				}
				if(!resultMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
					if(resultMap.get(RESPONSE_MESSAGE_FIELD).equals(errorMsg)) {
						throw exception;
					}
					else {
						throw new PinterestRuntimeException(errorMessage + resultMap.get(RESPONSE_MESSAGE_FIELD));
					}
				}
				return resultMap;
			}
		}
		
		APIRequestBuilder(String url) {
			this.url = url;
		}
		
		public APIRequestBuilder setProtocol(Protocol protocol) {
			this.protocol = protocol;
			return this;
		}
		public APIRequestBuilder setMethod(Method method) {
			this.method = method;
			return this;
		}
		public APIRequestBuilder setMethod(Method method, Object requestEntity) {
			this.method = method;
			this.requestEntity = requestEntity;
			return this;
		}
		public APIRequestBuilder setHttpSuccessStatus(Status httpSuccessStatus) {
			this.httpSuccessStatus = httpSuccessStatus;
			return this;
		}
		public APIRequestBuilder setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
			return this;
		}
		public APIRequestBuilder addExceptionMapping(Status status, PinterestRuntimeException exception) {
			exceptionMap.put(status, exception);
			return this;
		}
		public APIRequestBuilder setMediaType(MediaType mediaType) {
			this.mediaType = mediaType;
			return this;
		}
		public APIRequestBuilder setAjaxUsage(boolean ajaxUsage) {
			this.ajaxUsage = ajaxUsage;
			return this;
		}
		
		public APIResponse build() {
			WebResource.Builder builder = getWR(protocol, url, ajaxUsage);
			if(mediaType != null) {
				builder = builder.type(mediaType);
			}
			ClientResponse response = null;
			switch(method) {
				case GET :
					response = builder.get(RESPONSE_CLASS);
					break;
				case POST :
					response = builder.post(RESPONSE_CLASS, requestEntity);
					break;
				case DELETE :
					response = builder.delete(RESPONSE_CLASS);
					break;
				default :
					throw new PinterestRuntimeException("Unknown HTTP method");
			}
			Status status = Status.fromStatusCode(response.getStatus());
			if(!status.equals(httpSuccessStatus)) {
				if(exceptionMap.containsKey(status)) {
					throw exceptionMap.get(status);
				}
				else {
					throw new PinterestRuntimeException(response, errorMessage + BAD_SERVER_RESPONSE);
				}
			}
			return new APIResponse(response);
		}
		
	}
	
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
	
	protected FormDataBodyPart createImageBodyPart(File imgFile) {
		String[] mimeInfo = new MimetypesFileTypeMap().getContentType(imgFile).split("/");
		MediaType imageType = new MediaType(mimeInfo[0], mimeInfo[1]);
		FormDataBodyPart f = new FormDataBodyPart(
	                FormDataContentDisposition.name("img").fileName(imgFile.getName()).build(),
	                imgFile, imageType);
		return f;
	}
	
	protected Form getSwitchForm(String parameter, boolean state) {
		Form switchForm = new Form();
		switchForm.add("bla", "bla");
		if(!state) {
			switchForm.add(parameter, 1);
		}
		return switchForm;
	}
	
	
}
