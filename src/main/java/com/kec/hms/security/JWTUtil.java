package com.kec.hms.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTUtil {
	private final String SECRET_KEY="mysecretkeymysecretkeymysecretkey";
	private final long EXPIRATION=1000*60*10;
	
	public String generateToken(String username, String role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256)//sigm token using secret key and hs256 algorithm,only someone with this secret key can create or gverify this token
                .compact();
    }
	
	 public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
	            return true;
	        } catch (JwtException e) {
	            return false;
	        }
	    }
	
	public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes())
                .build()
        		.parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes())
                .build()
        		.parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
	
}
