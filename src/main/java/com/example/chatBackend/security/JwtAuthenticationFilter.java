package com.example.chatBackend.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.chatBackend.dao.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired private JwtUtil jwtUtil;
	@Autowired private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException , IOException {
		
//		System.out.println("Jwt Filter triggered...");
		
		String header = req.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer ")) {
			try {	
				String token = header.substring(7);
				String username = jwtUtil.extractUsername(token);
				
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					
					if(jwtUtil.validToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						
						SecurityContext context = SecurityContextHolder.createEmptyContext();
	                    context.setAuthentication(auth);
	                    SecurityContextHolder.setContext(context);
	                    
	                    // ✅ Update lastSeen
                        userRepository.findByuserName(username).ifPresent(user -> {
                            user.setLastSeen(LocalDateTime.now());
                            userRepository.save(user);
                        });
					}
				}
			}catch (Exception e) {
	            SecurityContextHolder.clearContext();
	            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            res.setContentType("application/json");
	            res.getWriter().write("{\"error\":\"Invalid or expired token\"}");
	            return;
	        }		
		}
		chain.doFilter(	req, res);
	}
	
}
