package com.kec.hms.model;

public class AuthRequest {

    private String username;
    private String password;

    // Default constructor (required for Spring to map JSON)
    public AuthRequest() {}

    // Constructor with fields (optional)
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
