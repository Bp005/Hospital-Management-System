package com.kec.hms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.dto.AuthRequest;
import com.kec.hms.model.User;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.security.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	
	 @PostMapping("/register")
	    public String register(@RequestBody AuthRequest request) {
	        Optional<User> existing = userRepo.findByUsername(request.getUsername());
	        if (existing.isPresent()) {
	            return "Username already exists";
	        }

	        User user = new User();
	        user.setUsername(request.getUsername());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRole(request.getRole());
	        userRepo.save(user);
	        return "User registered successfully";
	    }
	
	 
	 // Login user
	    @PostMapping("/login")
	    public String login(@RequestBody AuthRequest request) {
	        Optional<User> userOpt = userRepo.findByUsername(request.getUsername());
	        if (userOpt.isEmpty()) throw new RuntimeException("User not found");

	        User user = userOpt.get();

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
	            throw new RuntimeException("Invalid password");

	        return jwtUtil.generateToken(user.getUsername(), user.getRole());
	    }

	    // DTO for requests
	    static class AuthRequest {
	        private String username;
	        private String password;
	        private String role;

	        public String getUsername() { return username; }
	        public void setUsername(String username) { this.username = username; }

	        public String getPassword() { return password; }
	        public void setPassword(String password) { this.password = password; }

	        public String getRole() { return role; }
	        public void setRole(String role) { this.role = role; }
	    }
	
}
