package com.example.chatBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatBackend.beans.UserChatRoom;
import com.example.chatBackend.service.UserChatRoomService;

@RestController
@RequestMapping("/userchatrooms")
//@CrossOrigin(origins = "http://localhost:5173")

public class UserChatRoomController {
	
	@Autowired
	private UserChatRoomService userChatRoomService;
	
	// add user 
	@PostMapping("/add")
	public UserChatRoom addUserToChatRoom(@RequestParam Long userId, @RequestParam Long roomId, @RequestParam String role) {
		return userChatRoomService.addUserChatRoom(userId, roomId, role);
	}
	
	
}
