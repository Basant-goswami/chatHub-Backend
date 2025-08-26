package com.example.chatBackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatBackend.beans.User;
import com.example.chatBackend.dao.UserRepository;
import com.example.chatBackend.dto.UserDTO;
import com.example.chatBackend.security.CustomUserDetails;
import com.example.chatBackend.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController{
	
	@Autowired AuthService authService;
	@Autowired UserRepository userRepository;
		
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user){
		String result = authService.register(user);
		if(result.equals("User registered successfully!")) {
			return ResponseEntity.ok(result);
		} else { 
			return ResponseEntity.status(401).body(result);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		
		try {
			String token = authService.loginAndGenerateToken(user.getUserName(), user.getPassword());
//			UserDTO response = new UserDTO(result);
			return ResponseEntity.ok(Map.of("token", token));
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return authService.getAllUsers();
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth principal se user details : "+auth.getPrincipal());

	    if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
	        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
	        User user = userDetails.getUser(); // original User object
	        UserDTO dto = new UserDTO(user);
	        return ResponseEntity.ok(dto);
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
	}
	
	
	@PutMapping("/update-profile")
	public ResponseEntity<?> updateProfile(@RequestBody UserDTO updatedUser) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
	        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
	        User user = userDetails.getUser();
	        
	        if(userRepository.existsByemail(updatedUser.getEmail())) {
				return ResponseEntity.status(401).body("Email is already exists");
			}

	        user.setFullName(updatedUser.getFullName());
	        user.setEmail(updatedUser.getEmail());
	        user.setProfileImageUrl("https://api.dicebear.com/7.x/notionists/svg?seed=" + user.getUserName());

	        authService.save(user);
	        return ResponseEntity.ok(new UserDTO(user));
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	}
	
	@PutMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
	    String oldPassword = request.get("oldPassword");
	    String newPassword = request.get("newPassword");

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
	        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
	        User user = userDetails.getUser();

	        if (!authService.checkPassword(user, oldPassword)) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
	        }

	        user.setPassword(authService.encodePassword(newPassword));
	        authService.save(user);
	        return ResponseEntity.ok("Password updated successfully");
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	}

}

