package com.example.chatBackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.beans.UserChatRoom;
import com.example.chatBackend.dao.ChatRoomRepository;
import com.example.chatBackend.dao.UserChatRoomRepository;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private UserChatRoomService userChatRoomService;
	
	
	// creating new Chatroom 
	public ChatRoom createChatRoom(String name, boolean isGroup) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setName(name);
		chatRoom.setGroup(isGroup);
		return chatRoomRepository.save(chatRoom);
	}
	
	public Optional<ChatRoom> getChatRoom(Long id) {
		return chatRoomRepository.findById(id);
	}
	
	public List<ChatRoom> getChatRoomsForUser(Long userId){
        List<UserChatRoom> userRooms = userChatRoomRepository.findByUserId(userId);
        return userRooms.stream()
	        .map(UserChatRoom::getChatRoom)
	        .collect(Collectors.toList());   
	}
	
	public ChatRoom createRoomWithUsers(String name, boolean isGroup, Long createdBy, List<Long> memberIds) {
		ChatRoom chatRoom = createChatRoom(name, isGroup);
		for(Long userId : memberIds) {
			String role = userId.equals(createdBy) ? "ADMIN" : "MEMBER";
			userChatRoomService.addUserChatRoom(userId, chatRoom.getId(), role);
		}
		return chatRoom;
	}
}
