package com.sakda.chineselearning.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;

	@Override
	public String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getEmail())
				.issuedAt(new Date()) 
				.expiration(new Date(
						System.currentTimeMillis() + expiration
				))
				.signWith(getSigningKey())
				.compact();
	}
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(
				secret.getBytes(StandardCharsets.UTF_8)
				);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	@Override
	public String extractUsername(String token) {
		
		return extractAllClaims(token)
	            .getSubject();
	}
	
	private boolean isTokenExpired(String token) {

	    return extractAllClaims(token)
	            .getExpiration()
	            .before(new Date());
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {

	    String username = extractUsername(token);

	    return username.equals(userDetails.getUsername())
	            && !isTokenExpired(token);
	}
	

}
