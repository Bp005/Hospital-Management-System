package com.kec.hms.dto;

/**
 * DTO for returning doctor profile data
 * Simplified version
 */
public class DoctorProfileResponse {
    
    // User account information
    private String username;
    private String role;
    
    // Doctor professional information
    private String name;
    private String specialization;
    private String email;
    
    // Helper flag
    private boolean profileComplete;
    
    // ============================================
    // CONSTRUCTORS
    // ============================================
    
    public DoctorProfileResponse() {
        this.profileComplete = false;
    }
    
    // Constructor when profile doesn't exist
    public DoctorProfileResponse(String username, String role) {
        this.username = username;
        this.role = role;
        this.profileComplete = false;
    }
    
    // Constructor when profile exists
    public DoctorProfileResponse(String username, String role, String name,
                                String specialization, String email) {
        this.username = username;
        this.role = role;
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.profileComplete = (name != null && !name.isEmpty());
    }
    
    // ============================================
    // GETTERS AND SETTERS
    // ============================================
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isProfileComplete() {
        return profileComplete;
    }
    
    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }
}