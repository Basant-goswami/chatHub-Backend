package com.example.chatBackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.chatBackend.beans.ChatRoom;
import com.example.chatBackend.beans.Message;
import com.example.chatBackend.beans.MessageStatus;

import jakarta.transaction.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChatRoomOrderByMsgAtAsc(ChatRoom chatRoom);
	
	@Modifying
	@Query("UPDATE Message m SET m.status = :status WHERE m.id = :id")
	void updateMessageStatus(@Param("id") Long id, @Param("status") MessageStatus status);

	
	@Modifying
    @Transactional
    @Query("UPDATE Message m SET m.status = :status WHERE m.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") MessageStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.status = 'seen' WHERE m.chatRoom.id = :roomId AND m.sender <> :userName")
    void markMessagesAsSeen(@Param("roomId") Long roomId, @Param("userName") String userName);
}