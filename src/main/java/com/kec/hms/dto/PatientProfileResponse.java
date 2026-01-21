package com.kec.hms.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class PatientProfileResponse {
    private String username;
    private String role;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String address;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private boolean profileComplete;
    
    // Constructor when profile doesn't exist
    public PatientProfileResponse(String username, String role) {
        this.username = username;
        this.role = role;
        this.profileComplete = false;
    }
    
    // Constructor when profile exists
    public PatientProfileResponse(String username, String role, String name,
                                 Integer age, String gender, String phone,
                                 String address, LocalDate dob) {
        this.username = username;
        this.role = role;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.profileComplete = true;
    }
    
    // All getters and setters...
}