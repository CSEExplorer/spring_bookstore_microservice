package com.bookstore.account_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtService {

	private String SECRET_KEY = "ThisIsASecretKeyForJwtDontShareIt1234567890"; // should be 32+ characters

	private Key key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

    public String generateToken(String email, boolean isAdmin) {
	    return Jwts.builder()
	            .setSubject(email)
	            .claim("isAdmin", isAdmin) // 👈 Custom claim
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
	            .signWith(key)
	            .compact();
	}


	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean isTokenValid(String token, String userEmail) {
		String extractedEmail = extractEmail(token);
		return (extractedEmail.equals(userEmail) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
				.getExpiration();
		return expiration.before(new Date());
	}
}
