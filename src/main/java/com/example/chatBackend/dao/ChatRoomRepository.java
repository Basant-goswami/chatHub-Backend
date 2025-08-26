package com.example.chatBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatBackend.beans.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

}
