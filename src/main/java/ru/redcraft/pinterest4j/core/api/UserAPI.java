package ru.redcraft.pinterest4j.core.api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.redcraft.pinterest4j.NewUserSettings;
import ru.redcraft.pinterest4j.User;
import ru.redcraft.pinterest4j.core.api.AdditionalUserSettings.Gender;
import ru.redcraft.pinterest4j.core.api.components.UserBuilder;
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
	private static final String USER_FOLLOW_ERROR = "USER FOLLOW ERROR: ";
	
	public UserAPI(PinterestAccessToken accessToken, InternalAPIManager apiManager) {
		super(accessToken, apiManager);
	}
	
	private Document getUserInfoPage(String userName) {
		Document doc = null;
		ClientResponse response = getWR(Protocol.HTTP, userName + "/").get(ClientResponse.class);
		if(response.getStatus() == Status.OK.getStatusCode()) {
			doc = Jsoup.parse(response.getEntity(String.class));
		}
		else if(response.getStatus() ==  Status.NOT_FOUND.getStatusCode()) {
			throw new PinterestUserNotFoundException(userName);
		}
		else {
			throw new PinterestRuntimeException(
					response, 
					USER_OBTAINING_ERROR + BAD_SERVER_RESPONSE);
		}
		return doc;
	}
	
	public UserBuilder getCompleteUser(String userName) {
		LOG.debug("Getting all info for username " + userName);
		UserBuilder builder = new UserBuilder();
		builder.setUserName(userName);
		
		Document doc = getUserInfoPage(userName);
		
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
		
		Element description = userInfo.getElementsByTag("p").first();
		builder.setDescription(description != null ? description.text() : null);
		Element twitter = userInfo.select("a.twitter").first();
		builder.setTwitterURL(twitter != null ? twitter.attr("href") : null);
		Element facebook = userInfo.select("a.facebook").first();
		builder.setFacebookURL(facebook != null ? facebook.attr("href") : null);
		Element website = userInfo.select("a.website").first();
		builder.setSiteURL(website != null ? website.attr("href") : null);
		Element location = userInfo.select("li#ProfileLocation").first();
		builder.setLocation(location != null ? location.text() : null);
		
		builder.setLikesCount(Integer.valueOf(doc.select("div#ContextBar").first().getElementsByTag("li").get(2).getElementsByTag("strong").first().text()));
		
		return builder;
	}
	
	public User getUserForName(String userName) {
		return new LazyUser(getCompleteUser(userName), getApiManager());
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
	
	private FormDataMultiPart createUserForm(NewUserSettings settings) {
		FormDataMultiPart multipartForm = new FormDataMultiPart();
		AdditionalUserSettings adtSettings = getUserAdtSettings();
		multipartForm.bodyPart(new FormDataBodyPart("csrfmiddlewaretoken", getAccessToken().getCsrfToken().getValue())); 
		multipartForm.bodyPart(new FormDataBodyPart("email", adtSettings.getEmail()));
		multipartForm.bodyPart(new FormDataBodyPart("gender", adtSettings.getGender().name().toLowerCase(PINTEREST_LOCALE)));
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
	
	public User updateUser(NewUserSettings settings) {
		LOG.debug(String.format("Updating user=%s with settings=%s", getAccessToken().getLogin(), settings));
		FormDataMultiPart userUpdateForm = createUserForm(settings);
		ClientResponse response = getWR(Protocol.HTTPS, "settings/", false).type(MediaType.MULTIPART_FORM_DATA)
																   .post(ClientResponse.class, userUpdateForm);
		LOG.debug(response.getEntity(String.class) + response.getStatus());
		User newUser = getUserForName(getAccessToken().getLogin());
		LOG.debug("User updated. New user info: " + newUser);
		return newUser;
	}

	public void followUser(User user, boolean follow) {
		LOG.debug(String.format("Setting follow on user = %s to = %s", user, follow));
		ClientResponse response = getWR(Protocol.HTTP, user.getUserName() + "/follow/").post(ClientResponse.class, getSwitchForm("unfollow", follow));
		Map<String, String> responseMap = parseResponse(response, USER_FOLLOW_ERROR);
		if(!responseMap.get(RESPONSE_STATUS_FIELD).equals(RESPONSE_SUCCESS_STATUS)) {
			throw new PinterestRuntimeException(USER_FOLLOW_ERROR + responseMap.get(RESPONSE_MESSAGE_FIELD));
		}
		LOG.debug("Board follow mark set to " + follow);
	}

	public boolean isFollowing(User user) {
		LOG.debug("Checking following status for user=" + user);
		boolean followed = false;
		if(getUserInfoPage(user.getUserName()).select("a.unfollowuserbutton").size() == 1) {
			followed = true;
		}
		LOG.debug("Following state is " + followed);
		return followed;
	}
	
}
