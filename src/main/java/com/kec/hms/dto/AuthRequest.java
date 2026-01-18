package com.kec.hms.dto;

public class AuthRequest {

    private String username;
    private String password;
    private String role;

    // Default constructor (required for Spring to map JSON)
    public AuthRequest() {}

    // Constructor with fields (optional)
    public AuthRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role=role;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() { return role; }          // <-- getter
    public void setRole(String role) { this.role = role; }
    
}
