package com.example.chatBackend.Config;


import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.chatBackend.dao.UserRepository;

@Component
public class WebSocketEventListener {

    private final UserRepository userRepository;

    public WebSocketEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Principal principal = (Principal) event.getUser();
        if (principal != null) {
            userRepository.findByuserName(principal.getName()).ifPresent(user -> {
                user.setOnline(true);
                userRepository.save(user);
            });
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal principal = (Principal) event.getUser();
        if (principal != null) {
            userRepository.findByuserName(principal.getName()).ifPresent(user -> {
                user.setOnline(false);
                user.setLastSeen(LocalDateTime.now());
                userRepository.save(user);
            });
        }
    }
}
