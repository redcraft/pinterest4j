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
package ru.redcraft.pinterest4j.core.api;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.redcraft.pinterest4j.core.api.BoardAPI;

public class BoardAPITest {

	@Test
	public void testCreateLink() {
		String login = "user";
		assertEquals("/user/new-board/", BoardAPI.createLink("New Board", login));
		assertEquals("/user/er-d-d-f-d-23-3/", BoardAPI.createLink("er@,d,d.F.d*23 3", login));
	}

}
