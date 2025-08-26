package com.example.chatBackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatBackend.beans.UserChatRoom;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long>{

	List<UserChatRoom> findByUserId(Long userId);

//	List<UserChatRoom> findByUser_Id(Long userId);
}
