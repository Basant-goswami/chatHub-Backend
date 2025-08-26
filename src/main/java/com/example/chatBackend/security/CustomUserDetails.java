package com.example.chatBackend.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.chatBackend.beans.User;

public class CustomUserDetails implements  UserDetails{
	
	private final User user;
	
	 public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now we return empty authority list, you can modify as per role later
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // match this with your field name
    }

    @Override
    public String getUsername() {
        return user.getUserName(); // or user.getUsername() based on your logic
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
