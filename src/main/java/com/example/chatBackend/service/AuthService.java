package com.example.chatBackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chatBackend.beans.User;
import com.example.chatBackend.dao.UserRepository;
import com.example.chatBackend.security.JwtUtil;



@Service
public class AuthService {

	@Autowired UserRepository userRepository;
	@Autowired private JwtUtil jwtUtil;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String register(User user) {
		if(userRepository.existsByemail(user.getEmail())) {
			return "Email is already exits";
		}
		if(userRepository.existsByuserName(user.getUserName())) {
			return "Username is already exits";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// src={`https://api.dicebear.com/7.x/avataaars/svg?seed=${room.name}`}
		
		user.setProfileImageUrl("https://api.dicebear.com/7.x/notionists/svg?seed=" + user.getUserName());
		user.setOnline(false);
		user.setLastSeen(LocalDateTime.now());
//		userRepo.save(user);
		
		userRepository.save(user);
		return "User registered successfully!";
	}
	
	public String loginAndGenerateToken(String loginId, String password) {
		Optional<User> isUser = userRepository.findByUserNameOrEmailIgnoreCase(loginId);
		if(isUser.isPresent()) {
			User user = isUser.get();
			if(passwordEncoder.matches(password, user.getPassword())) {
				return jwtUtil.generateToken(user.getUserName());
			} else {
				throw new RuntimeException("Invalid credentials");
			}
		} else {
			throw new RuntimeException("User not found!");
		}
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getCurrentUser(String username ) {
		return userRepository.findByuserName(username);
	}
	
	
//	@Autowired private PasswordEncoder passwordEncoder;

	public User save(User user) {
	    return userRepository.save(user);
	}

	public boolean checkPassword(User user, String rawPassword) {
	    return passwordEncoder.matches(rawPassword, user.getPassword());
	}

	public String encodePassword(String password) {
	    return passwordEncoder.encode(password);
	}

}
