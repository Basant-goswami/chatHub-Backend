package com.example.chatBackend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.chatBackend.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE LOWER(u.userName) = LOWER(:input) OR LOWER(u.email) = LOWER(:input)")
	Optional<User> findByUserNameOrEmailIgnoreCase(@Param("input") String input);
	
	Optional<User> findByuserName(String userName);
	boolean existsByemail(String email);
	boolean existsByuserName(String userName);
}
