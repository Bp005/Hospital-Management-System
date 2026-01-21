package com.kec.hms.dto;

/**
 * DTO for updating doctor profile
 * Simplified version without phone, license, experience
 */
public class DoctorUpdateDto {
    
    private String name;
    private String specialization;
    private String email;
    
    // ============================================
    // CONSTRUCTORS
    // ============================================
    
    public DoctorUpdateDto() {}
    
    public DoctorUpdateDto(String name, String specialization, String email) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
    }
    
    // ============================================
    // GETTERS AND SETTERS
    // ============================================
    
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
}