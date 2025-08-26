	package com.example.chatBackend.security;
	
	import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
	
	@Component
	public class JwtUtil {
		
		
	//	private static String Secret_key="hellohellobolkemereaajubaajudolkediwaniladkihaijodekheterenajrokepiche";
		
//		private final String SECRET = "u1jz5wJzMv7Y2lRcjImJTwGq3Roj+PbHq/5Kj3TCYbU="; // should be secure and 256-bit
//		private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
		
		private final Key secretKey;

	    // Spring injects the value from application.properties -> jwt.secret
	    public JwtUtil(@Value("${jwt.Secret_key}") String secret) {
	        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	    }
		
		public String generateToken(String username) {
			return Jwts.builder()
					.setSubject(username)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
					.signWith(secretKey)
					.compact();
		}
		                                 
		public String extractUsername(String token) {
			return Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
		}
		
		public boolean validToken(String token, UserDetails userDetails) {
			final String username = extractUsername(token);
			return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		}
		
		public boolean isTokenExpired(String token) {
			Date exp = Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getExpiration();
			return exp.before(new Date());
		}
	}
	
