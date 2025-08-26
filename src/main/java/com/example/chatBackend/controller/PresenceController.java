package com.example.chatBackend.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatBackend.beans.User;
import com.example.chatBackend.dao.UserRepository;
import com.example.chatBackend.dto.UserDTO;

@RestController	
public class PresenceController {

	@Autowired
    private UserRepository userRepo;

    @MessageMapping("/presence")
    @SendTo("/topic/status")
    public UserDTO handlePresence(@Payload Map<String, String> message, Principal principal) {
        User user = userRepo.findByuserName(principal.getName()).orElse(null);
        if (user != null) {
            boolean isOnline = Boolean.parseBoolean(message.get("online"));
            user.setOnline(isOnline);
//            if (!isOnline) {
//                user.setLastSeen(LocalDateTime.now());
//            }
            user.setLastSeen(isOnline ? null : LocalDateTime.now());
            userRepo.save(user);
            return new UserDTO(user);
        }
        return null;
    }
}
