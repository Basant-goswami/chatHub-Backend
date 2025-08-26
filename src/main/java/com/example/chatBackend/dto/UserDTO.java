package com.example.chatBackend.dto;

import java.time.LocalDateTime;

import com.example.chatBackend.beans.User;

public class UserDTO {
	
	private Long id;
    private String userName;
    private String email;
    private String fullName;
    private boolean isActive;
    private Boolean online;
    private LocalDateTime lastSeen;
    private String profileImageUrl;
    
    // ✅ Default constructor (required for deserialization)
    public UserDTO() {
    }

    // constructor
    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.isActive = user.isActive();
        this.online = user.getOnline();
        this.lastSeen = user.getLastSeen();
        this.profileImageUrl = user.getProfileImageUrl();
    }

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public LocalDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(LocalDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
}
