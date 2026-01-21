package com.kec.hms.controller;

import com.kec.hms.dto.DoctorProfileResponse;
import com.kec.hms.dto.DoctorUpdateDto;
import com.kec.hms.model.Doctor;
import com.kec.hms.model.User;
import com.kec.hms.repository.DoctorRepository;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    // ============================================
    // PUBLIC ENDPOINT - For Patients to See Doctors
    // ============================================
    
    /**
     * GET /api/v1/doctors/available
     * Anyone authenticated can see list of doctors (for booking appointments)
     * Returns only basic info (id, name, specialization)
     */
    @GetMapping("/available")
    public ResponseEntity<List<DoctorBasicInfo>> getAvailableDoctors() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            List<DoctorBasicInfo> basicInfoList = new ArrayList<>();
            
            for (Doctor doctor : doctors) {
                // Only include doctors who have completed their profile
                if (doctor.getName() != null && !doctor.getName().isEmpty()) {
                    DoctorBasicInfo info = new DoctorBasicInfo();
                    info.setId(doctor.getId());
                    info.setName(doctor.getName());
                    info.setSpecialization(doctor.getSpecialization());
                    basicInfoList.add(info);
                }
            }
            
            return ResponseEntity.ok(basicInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ============================================
    // ADMIN ENDPOINTS - Full Doctor Management
    // ============================================
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }
    
    // ============================================
    // DOCTOR PROFILE ENDPOINTS (/me)
    // ============================================
    
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        try {
            String username = auth.getName();
            System.out.println("Fetching doctor profile for: " + username);
            
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Doctor doctor = doctorRepo.findByUser(user).orElse(null);
            
            if (doctor == null) {
                DoctorProfileResponse response = new DoctorProfileResponse(
                    user.getUsername(),
                    user.getRole()
                );
                return ResponseEntity.ok(response);
            }
            
            DoctorProfileResponse response = new DoctorProfileResponse(
                user.getUsername(),
                user.getRole(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error fetching profile: " + e.getMessage());
        }
    }
    
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            @RequestBody DoctorUpdateDto dto,
            Authentication auth) {
        
        try {
            String username = auth.getName();
            System.out.println("Updating doctor profile for: " + username);
            
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Doctor doctor = doctorRepo.findByUser(user).orElse(null);
            
            if (doctor == null) {
                doctor = new Doctor();
                doctor.setUser(user);
            }
            
            doctor.setName(dto.getName());
            doctor.setSpecialization(dto.getSpecialization());
            doctor.setEmail(dto.getEmail());
            
            Doctor savedDoctor = doctorRepo.save(doctor);
            
            DoctorProfileResponse response = new DoctorProfileResponse(
                user.getUsername(),
                user.getRole(),
                savedDoctor.getName(),
                savedDoctor.getSpecialization(),
                savedDoctor.getEmail()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error updating profile: " + e.getMessage());
        }
    }
    
    // ============================================
    // INNER CLASS - Basic Doctor Info DTO
    // ============================================
    
    /**
     * Simple DTO for public doctor listing
     * Only shows what patients need to book appointments
     */
    public static class DoctorBasicInfo {
        private Long id;
        private String name;
        private String specialization;
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
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
    }
}