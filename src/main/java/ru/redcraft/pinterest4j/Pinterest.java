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
package ru.redcraft.pinterest4j;

import ru.redcraft.pinterest4j.core.api.AsyncPinPayload;



public interface Pinterest {

	//Boards
	
	Board getBoard(String url);
	
	Board createBoard(NewBoard newBoard);
	
	Board updateBoard(Board board, String title, String description, BoardCategory category);
	
	void deleteBoard(Board board);
	
	void followBoard(Board board);
	
	void unfollowBoard(Board board);
	
	boolean isFollowing(Board board);
	
	//Pin
	
	Pin addPin(Board board, NewPin newPin);
	
	void deletePin(Pin pin);
	
	Pin updatePin(Pin pin, String description, Double price, String link, Board board);
	
	Pin getPin(long id);
	
	void getAsyncPin(long id, AsyncPinPayload payload);
	
	Pin repin(Pin pin, Board board, String description);
	
	void likePin(Pin pin);
	
	void unlikePin(Pin pin);
	
	boolean isLiked(Pin pin);
	
	Comment addComment(Pin pin, String comment);
	
	void deleteComment(Comment comment);
	
	Iterable<Pin> getPinsByCategory(BoardCategory category);
	
	Iterable<Pin> getPopularPins();
	
	//User
	
	User getUser(String userName);
	
	User getUser();
	
	User updateUser(NewUserSettings settings);
	
	void followUser(User user);
	
	void unfollowUser(User user);
	
	boolean isFollowing(User user);
	
	//Gloabal
	
	void close();
	
}
