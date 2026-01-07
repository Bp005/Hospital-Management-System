package com.kec.hms.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component//this means spring managed object
public class JwtUtil {
	private final String SECRET_KEY="mysecretkeymysecretkeymysecretkey";
	public String generateToken(String username) {
		return Jwts.builder()//user le username halcha, ani esle chaou JWT string banayera op dincha. random looking text op
				.setSubject(username)//this token belongs to this user
				.setIssuedAt(new Date())//when token was issued,helps with security,debugging and token validation
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60)//token expires in 1 hour
				)// 1hour
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256)//sigm token using secret key and hs256 algorithm,only someone with this secret key can create or gverify this token
				.compact();//converts token into a string
				
	
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
}
