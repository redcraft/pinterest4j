package ru.redcraft.pinterest4j.core.api;

import ru.redcraft.pinterest4j.core.api.CoreAPI.Protocol;

import com.sun.jersey.api.client.WebResource;

public interface PinterestServicePovider {

	public WebResource.Builder getWR(Protocol protocol, String url, PinterestAccessToken accessToken, boolean useAJAX);
	
}
