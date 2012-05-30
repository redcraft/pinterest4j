package ru.redcraft.pinterest4j.core.api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.UserBuilder;
import ru.redcraft.pinterest4j.core.UserImpl;
import ru.redcraft.pinterest4j.core.UserSettings;
import ru.redcraft.pinterest4j.core.api.AdditionalUserSettings.Gender;
import ru.redcraft.pinterest4j.exceptions.PinterestRuntimeException;
import ru.redcraft.pinterest4j.exceptions.PinterestUserNotFoundException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

public class UserAPI extends CoreAPI {

	private static final String USER_FOLLOWING_PROP_NAME = "pinterestapp:following";
	private static final String USER_FOLLOWERS_PROP_NAME = "pinterestapp:followers";
	private static final String USER_BOARDS_PROP_NAME = "pinterestapp:boards";
	private static final String USER_PINS_PROP_NAME = "pinterestapp:pins";
	private static final String USER_IMAGE_PROP_NAME = "og:image";
	
	private static final Logger LOG = Logger.getLogger(UserAPI.class);
	
	private static final String USER_OBTAINING_ERROR = "USER OBTAINING ERROR: ";
	
	public UserAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}
	
	public UserImpl getCompleteUser(User user) {
		LOG.debug("Getting all info for lazy user " + user);
		UserBuilder builder = new UserBuilder();
		builder.setUserName(user.getUserName());
		ClientResponse response = getWR(Protocol.HTTP, user.getUserName() + "/").get(ClientResponse.class);
		
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Document doc = Jsoup.parse(response.getEntity(String.class));
			
			Map<String, String> metaMap = new HashMap<String, String>();
			for(Element meta : doc.select("meta")) {
				metaMap.put(meta.attr("property"), meta.attr("content"));
			}
			builder.setFollowingCount(Integer.valueOf(metaMap.get(USER_FOLLOWING_PROP_NAME)));
			builder.setFollowersCount(Integer.valueOf(metaMap.get(USER_FOLLOWERS_PROP_NAME)));
			builder.setBoardsCount(Integer.valueOf(metaMap.get(USER_BOARDS_PROP_NAME)));
			builder.setPinsCount(Integer.valueOf(metaMap.get(USER_PINS_PROP_NAME)));
			builder.setImageURL(metaMap.get(USER_IMAGE_PROP_NAME));
			
			Element userInfo = doc.select("div.content").first();
			builder.setFullName(userInfo.getElementsByTag("h1").first().text());
			builder.setDescription(userInfo.getElementsByTag("p").first().text());
			
			Element twitter = userInfo.select("a.twitter").first();
			builder.setTwitterURL(twitter != null ? twitter.attr("href") : null);
			Element facebook = userInfo.select("a.facebook").first();
			builder.setFacebookURL(facebook != null ? facebook.attr("href") : null);
			Element website = userInfo.select("a.website").first();
			builder.setSiteURL(website != null ? website.attr("href") : null);
			Element location = userInfo.select("li#ProfileLocation").first();
			builder.setLocation(location != null ? location.text() : null);
			
			builder.setLikesCount(Integer.valueOf(doc.select("div#ContextBar").first().getElementsByTag("li").get(2).getElementsByTag("strong").first().text()));
		}
		else if(response.getStatus() ==  Status.NOT_FOUND.getStatusCode()) {
			throw new PinterestUserNotFoundException(user);
		}
		else {
			throw new PinterestRuntimeException(
					response, 
					USER_OBTAINING_ERROR + BAD_SERVER_RESPONSE);
		}
		
		return builder.build();
	}
	
	public User getUserForName(String userName) {
		return getCompleteUser(new LazyUser(userName, this));
	}

	private AdditionalUserSettings getUserAdtSettings() {
		AdditionalUserSettings adtSettings = new AdditionalUserSettings();
		ClientResponse response = getWR(Protocol.HTTPS, "settings/", false).get(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Document doc = Jsoup.parse(response.getEntity(String.class));
			adtSettings.setEmail(doc.getElementById("id_email").attr(VALUE_TAG_ATTR));
			adtSettings.setFirstName(doc.getElementById("id_first_name").attr(VALUE_TAG_ATTR));
			adtSettings.setLastName(doc.getElementById("id_last_name").attr(VALUE_TAG_ATTR));
			adtSettings.setUserName(doc.getElementById("id_username").attr(VALUE_TAG_ATTR));
			adtSettings.setWebsite(doc.getElementById("id_website").attr(VALUE_TAG_ATTR));
			adtSettings.setLocation(doc.getElementById("id_location").attr(VALUE_TAG_ATTR));
			if(doc.getElementById("id_gender_0").hasAttr(UserAPI.CHECKED_TAG_ATTR)) {
				adtSettings.setGender(Gender.MALE);
			}
			if(doc.getElementById("id_gender_1").hasAttr(UserAPI.CHECKED_TAG_ATTR)) {
				adtSettings.setGender(Gender.FEMALE);
			}
			if(doc.getElementById("id_gender_2").hasAttr(UserAPI.CHECKED_TAG_ATTR)) {
				adtSettings.setGender(Gender.UNSPECIFIED);
			}
		}
		else {
			throw new PinterestRuntimeException(response, USER_OBTAINING_ERROR + "can't get additional settings");
		}
		return adtSettings;
	}
	
	private FormDataMultiPart createUserForm(UserSettings settings) {
		FormDataMultiPart multipartForm = new FormDataMultiPart();
		AdditionalUserSettings adtSettings = getUserAdtSettings();
		multipartForm.bodyPart(new FormDataBodyPart("csrfmiddlewaretoken", getAccessToken().getCsrfToken().getValue())); 
		multipartForm.bodyPart(new FormDataBodyPart("email", adtSettings.getEmail()));
		multipartForm.bodyPart(new FormDataBodyPart("gender", adtSettings.getGender().name().toLowerCase()));
		multipartForm.bodyPart(new FormDataBodyPart("username", adtSettings.getUserName()));
		
		multipartForm.bodyPart(new FormDataBodyPart("first_name", 
				settings.getFirstName() != null ? settings.getFirstName() : adtSettings.getFirstName()));
		multipartForm.bodyPart(new FormDataBodyPart("last_name", 
				settings.getLastName() != null ? settings.getLastName() : adtSettings.getLastName()));
		if(settings.getWebsite() != null || adtSettings.getWebsite() != null) {
			multipartForm.bodyPart(new FormDataBodyPart("website", 
					settings.getWebsite() != null ? settings.getWebsite() : adtSettings.getWebsite()));
		}
		if(settings.getLocation() != null || adtSettings.getLocation() != null) {
			multipartForm.bodyPart(new FormDataBodyPart("location", 
					settings.getLocation() != null ? settings.getLocation() : adtSettings.getLocation()));
		}
		if(settings.getAbout() != null) {
			multipartForm.bodyPart(new FormDataBodyPart("about", settings.getAbout()));
		}
		if(settings.getImage() != null) {
			multipartForm.bodyPart(createImageBodyPart(settings.getImage()));
		}
		return multipartForm;
	}
	
	public User updateUser(UserSettings settings) {
		LOG.debug(String.format("Updating user=%s with settings=%s", getAccessToken().getLogin(), settings));
		FormDataMultiPart userUpdateForm = createUserForm(settings);
		ClientResponse response = getWR(Protocol.HTTPS, "settings/", false).type(MediaType.MULTIPART_FORM_DATA)
																   .post(ClientResponse.class, userUpdateForm);
		LOG.debug(response.getEntity(String.class) + response.getStatus());
		User newUser = getUserForName(getAccessToken().getLogin());
		LOG.debug("User updated. New user info: " + newUser);
		return newUser;
	}
}
