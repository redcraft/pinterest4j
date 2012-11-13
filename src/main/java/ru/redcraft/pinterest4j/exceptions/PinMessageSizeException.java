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

public class PinMessageSizeException extends PinterestRuntimeException {

	private static final long serialVersionUID = -170707100325210641L;
	public static final String MSG_TOO_LONG = "Description message is greater then 500 characters";
	public static final String MSG_ZERO_SIZE = "Description message length is 0";

	public PinMessageSizeException(String msg) {
		super(msg);
		
	}
	
}
