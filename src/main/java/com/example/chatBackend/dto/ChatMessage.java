package com.example.chatBackend.dto;

public class ChatMessage {
	private Long id;
	private String content;
	private String sender;
	private Long chatRoomId;
	private String status;
	private String timestamp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ChatMessage() {}

	public ChatMessage(String content, String sender, Long chatRoomId) {
		super();
		this.content = content;
		this.sender = sender;
		this.chatRoomId = chatRoomId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
