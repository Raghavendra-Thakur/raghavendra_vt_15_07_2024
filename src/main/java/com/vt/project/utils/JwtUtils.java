package com.vt.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.vt.project.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component
public class JwtUtils {
	private static final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 1 hour in milliseconds

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	public String generateToken(User userDetails) {
		return Jwts.builder().setSubject(userDetails.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes()).compact();
	}

	public UserDetails getUserDetailsFromToken(String token) {
		return (UserDetails) Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Jws<Claims> getClaims(String token) {
		try {
			System.out.println(token);
			return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {

			throw new SecurityException("Token is expired", e);
		} catch (SignatureException e) {

			throw new SecurityException("Error parsing jwt token. Token is wrong");
		} catch (IllegalArgumentException | MalformedJwtException | UnsupportedJwtException e) {

			throw new SecurityException("Error parsing jwt token. Token is invalid", e);
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}
}
