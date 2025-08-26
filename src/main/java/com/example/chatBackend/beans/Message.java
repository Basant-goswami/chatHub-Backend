package com.example.chatBackend.beans;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String msg;
	
	@Column
	private LocalDateTime msgAt = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MessageStatus status = MessageStatus.SENT;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;
	
	public Message() {}

	public Message(Long id, String msg, LocalDateTime msgAt, User sender, ChatRoom chatRoom) {
		super();
		this.id = id;
		this.msg = msg;
		this.msgAt = msgAt;
		this.sender = sender;
		this.chatRoom = chatRoom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDateTime getMsgAt() {
		return msgAt;
	}

	public void setMsgAt(LocalDateTime msgAt) {
		this.msgAt = msgAt;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	
	public MessageStatus getStatus() {
	    return status;
	}

	public void setStatus(MessageStatus status) {
	    this.status = status;
	}
	
}
