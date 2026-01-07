package com.kec.hms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.dto.LoginRequest;
import com.kec.hms.model.AuthRequest;
import com.kec.hms.model.User;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return "user registered successfully";
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	    User user = userRepo.findByUsername(request.getUsername());
	    if (user == null) throw new RuntimeException("User not found");

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
	        throw new RuntimeException("Invalid password");

	    String token = jwtUtil.generateToken(user.getUsername());

	    return ResponseEntity.ok(Map.of(
	        "token", token,
	        "role", user.getRole() // <-- you can send role to frontend
	    ));
	}



}
