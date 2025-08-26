package com.example.chatBackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.beans.User;
import com.example.chatBackend.beans.UserChatRoom;
import com.example.chatBackend.dao.ChatRoomRepository;
import com.example.chatBackend.dao.UserChatRoomRepository;
import com.example.chatBackend.dao.UserRepository;

@Service
public class UserChatRoomService {

	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public UserChatRoom addUserChatRoom(Long userId, Long chatRoomId, String role) {
		
		UserChatRoom ucr = new UserChatRoom();
		Optional<User> user = userRepository.findById(userId);
		Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomId);
		
		if(!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User id " + userId + " is not present.");
		}
		
		if(!chatRoom.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat room id "+ chatRoom + " is not present.");
		}
		
		ucr.setUser(user.get());
		ucr.setChatRoom(chatRoom.get());
		ucr.setRole(role);
		return userChatRoomRepository.save(ucr);
	}
	
	
	
}