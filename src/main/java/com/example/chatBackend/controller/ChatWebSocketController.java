package com.example.chatBackend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.beans.Message;
import com.example.chatBackend.beans.MessageStatus;
import com.example.chatBackend.beans.User;
import com.example.chatBackend.dao.ChatRoomRepository;
import com.example.chatBackend.dao.MessageRepository;
import com.example.chatBackend.dao.UserRepository;
import com.example.chatBackend.dto.ChatMessage;
import com.example.chatBackend.service.MessageService;

@Controller
public class ChatWebSocketController {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	
	@MessageMapping("chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(ChatMessage chatMessage) {
		
		User user = userRepository.findByuserName(chatMessage.getSender()).orElseThrow();
		ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getChatRoomId()).orElseThrow();
		
		// save to the DB
		Message message = new Message();
		
		message.setChatRoom(chatRoom);
		message.setMsg(chatMessage.getContent());
		message.setSender(user);
		message.setMsgAt(LocalDateTime.now());
		message.setStatus(MessageStatus.SENT);
//		messageRepository.save(message);
//		return chatMessage;
		
		Message savedMsg = messageRepository.save(message);

	    // Convert to ChatMessage DTO with ID, msgAt and status
	    ChatMessage fullMessage = new ChatMessage();
	    fullMessage.setId(savedMsg.getId());
	    fullMessage.setSender(user.getUserName());
	    fullMessage.setContent(savedMsg.getMsg());
	    fullMessage.setChatRoomId(chatRoom.getId());
	    fullMessage.setTimestamp(savedMsg.getMsgAt().toString());
	    fullMessage.setStatus(savedMsg.getStatus().name());

	    return fullMessage;
	}
	
	@MessageMapping("/chat.messageDelivered")
	public void handleDeliveredStatus(@Payload Map<String, Object> payload) {
	    Long messageId = Long.parseLong(payload.get("messageId").toString());
	    messageService.updateStatus(messageId, "delivered");
	    
	 // Send the updated message back to all clients
	    Message updatedMessage = messageRepository.findById(messageId).orElseThrow();
	    messagingTemplate.convertAndSend("/topic/public", updatedMessage);
	}

	@MessageMapping("/chat.messageSeen")
	public void handleSeenStatus(@Payload Map<String, Object> payload) {
	    Long roomId = Long.parseLong(payload.get("roomId").toString());
	    String userName = payload.get("userName").toString();
	    messageService.markAsSeen(roomId, userName);
	    
	}

}