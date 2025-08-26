	package com.example.chatBackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.dto.CreateRequestRoom;
import com.example.chatBackend.service.ChatRoomService;

@RestController
@RequestMapping("/chatrooms")
//@CrossOrigin(origins = "http://localhost:5173")

public class ChatRoomController {

	@Autowired 
	private ChatRoomService chatRoomService;
	
	@PostMapping
	public ChatRoom createChatRoom(@RequestParam String name, @RequestParam(defaultValue="false") boolean isGroup){
		return chatRoomService.createChatRoom(name, isGroup);
	}
	
	@GetMapping("/{id}")
	public Optional<ChatRoom> getChatRoom(@PathVariable Long id) {
		return chatRoomService.getChatRoom(id);
	}
	
	@GetMapping("/user/{userId}")
    public List<ChatRoom> getChatRoomsByUserId(@PathVariable Long userId) {
        return chatRoomService.getChatRoomsForUser(userId);
    }
	
	@PostMapping("/createWithUsers")
	public ResponseEntity<?> createRoomWithUsers(@RequestBody CreateRequestRoom request){
		ChatRoom chatroom = chatRoomService.createRoomWithUsers(request.getName(), request.isGroup(), request.getCreatedBy(), request.getMemberIds());
		return ResponseEntity.ok(chatroom);
	}
}

