/**
 * Copyright (C) 2012 Maxim Gurkin <redmax3d@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.redcraft.pinterest4j.exceptions;

import com.sun.jersey.api.client.ClientResponse;

public class PinterestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8763514955708359725L;

	private static String buildMsg(int status, String response, String msg) {
		return String.format("Runtime error: %s. Response code: %d. Response: %s", msg, status, response);
	}
	
	public PinterestRuntimeException(String msg) {
		super(msg);
	}
	
	public PinterestRuntimeException(String msg, Exception e) {
		super(msg, e);
	}
	
	public PinterestRuntimeException(ClientResponse response, String msg) {
		this(buildMsg(response.getStatus(), response.getEntity(String.class), msg));
	}
	
	public PinterestRuntimeException(ClientResponse response, String msg,  Exception e) {
		this(buildMsg(response.getStatus(), response.getEntity(String.class), msg), e);
	}
	
}
