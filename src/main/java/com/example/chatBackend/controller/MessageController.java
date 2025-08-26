package com.example.chatBackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatBackend.beans.Message;
import com.example.chatBackend.beans.MessageStatus;
import com.example.chatBackend.service.MessageService;

@RestController
@RequestMapping("/messages")
//@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@PostMapping("/send")
	public Message sendMessage(@RequestParam String msg, @RequestParam Long userId, @RequestParam Long roomId) {
		return messageService.sendMessage(msg, userId, roomId);
	}
	
	@GetMapping("/chatroom/{roomId}")
	public List<Message> getMessagesByChatRoom(@PathVariable Long roomId){
		return messageService.getMessagesByChatRoom(roomId);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateMessageStatus(
	        @PathVariable Long id,
	        @RequestBody Map<String, String> body
	) {
	    String statusStr = body.get("status");
	    try {
	        MessageStatus status = MessageStatus.valueOf(statusStr.toUpperCase());
	        messageService.updateMessageStatus(id, status);
	        return ResponseEntity.ok("Message status updated to " + status);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Invalid status value");
	    }
	}

	
}
