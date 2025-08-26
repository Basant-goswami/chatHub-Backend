package com.example.chatBackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.beans.Message;
import com.example.chatBackend.beans.MessageStatus;
import com.example.chatBackend.beans.User;
import com.example.chatBackend.dao.ChatRoomRepository;
import com.example.chatBackend.dao.MessageRepository;
import com.example.chatBackend.dao.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	

	public Message sendMessage(String msg, Long userId, Long roomId) {
		
		Message mObj = new Message();
		Optional<User> user = userRepository.findById(userId);
		Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
		
		if(!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not present.");
		}
		if(!chatRoom.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat room is not present");
		}
		
		mObj.setMsg(msg);
		mObj.setChatRoom(chatRoom.get());
		mObj.setMsgAt(LocalDateTime.now());
		mObj.setSender(user.get());
		
		return messageRepository.save(mObj);
	}
	
	public List<Message> getMessagesByChatRoom(Long roomId){
		ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat room is not present"));
		return messageRepository.findByChatRoomOrderByMsgAtAsc(chatRoom);
		  
	}
	
	public void updateStatus(Long messageId, String status) {
		MessageStatus messageStatus = MessageStatus.valueOf(status.toUpperCase());
	    messageRepository.updateStatusById(messageId, messageStatus);
	}
	
	public void markAsSeen(Long roomId, String currentUserName) {
	    messageRepository.markMessagesAsSeen(roomId, currentUserName);
	}
	
	@Transactional
	public void updateMessageStatus(Long messageId, MessageStatus status) {
	    messageRepository.updateMessageStatus(messageId, status);
	}


}
