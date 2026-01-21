package com.kec.hms.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.dto.Patientupdatedto;
import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.PatientRepository;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = "http://localhost:3000")
public class patientController {
	
	@Autowired
	private PatientService patientservice;
	
	@Autowired
	private PatientRepository patientrepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	// ============================================
	// ADMIN/DOCTOR ENDPOINTS
	// ============================================
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@GetMapping
	public List<Patient> getAll() {
		return patientrepo.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@PostMapping
	public String createPatient(@RequestBody Patient patient) {
		System.out.println("Creating patient");
		patientservice.addPatient(patient);
		return "Patient added";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@GetMapping("/{id}")
	public Patient getPatientById(@PathVariable Long id) {
		System.out.println("Fetching patient by id: " + id);
		return patientservice.getPatientById(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@PutMapping("/{id}")
	public String updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		patientservice.updatePatient(id, patient);
		return "Update success";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@DeleteMapping("/{id}")
	public String deletePatient(@PathVariable Long id) {
		patientservice.deletePatient(id);
		return "Successfully deleted patient";
	}
	
	
	// ============================================
	// PATIENT PROFILE ENDPOINTS (/me)
	// ============================================
	
	/**
	 * GET /api/v1/patients/me
	 * Patient views their own profile
	 */
	// In your PatientController.java

	// ============================================
	// REMOVE @PreAuthorize from these methods:
	// ============================================

	@GetMapping("/me")  // ← No @PreAuthorize here!
	public ResponseEntity<?> getMyProfile(Authentication auth) {
	    try {
	        String username = auth.getName();
	        System.out.println("Fetching profile for user: " + username);
	        
	        User user = userRepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found: " + username));
	        
	        System.out.println("Found user with ID: " + user.getId());
	        
	        Patient patient = patientrepo.findByUser(user).orElse(null);
	        
	        if (patient == null) {
	            System.out.println("No patient profile found - returning empty DTO");
	            Patientupdatedto emptyDto = new Patientupdatedto();
	            return ResponseEntity.ok(emptyDto);
	        }
	        
	        System.out.println("Patient profile found: " + patient.getName());
	        Patientupdatedto dto = new Patientupdatedto();
	        dto.setName(patient.getName());
	        dto.setAge(patient.getAge());
	        dto.setGender(patient.getGender());
	        dto.setPhone(patient.getPhone());
	        dto.setAddress(patient.getAddress());
	        dto.setDob(patient.getDob());
	        
	        return ResponseEntity.ok(dto);
	        
	    } catch (Exception e) {
	        System.err.println("Error fetching profile: " + e.getMessage());
	        e.printStackTrace();
	        return ResponseEntity.badRequest()
	                .body("Error fetching profile: " + e.getMessage());
	    }
	}

	@PutMapping("/me")  // ← No @PreAuthorize here!
	public ResponseEntity<?> updateMyProfile(
	        @RequestBody Patientupdatedto dto, 
	        Authentication auth) {
	    
	    try {
	        String username = auth.getName();
	        System.out.println("Updating profile for user: " + username);
	        
	        User user = userRepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        Patient patient = patientrepo.findByUser(user).orElse(null);
	        
	        if (patient == null) {
	            System.out.println("Creating new patient profile");
	            patient = new Patient();
	            patient.setUser(user);  // CRITICAL: Link to user account
	        } else {
	            System.out.println("Updating existing patient profile");
	        }
	        
	        patient.setName(dto.getName());
	        patient.setAge(dto.getAge());
	        patient.setGender(dto.getGender());
	        patient.setPhone(dto.getPhone());
	        patient.setAddress(dto.getAddress());
	        patient.setDob(dto.getDob());
	        
	        Patient savedPatient = patientrepo.save(patient);
	        System.out.println("Patient profile saved with ID: " + savedPatient.getId());
	        
	        Patientupdatedto responseDto = new Patientupdatedto();
	        responseDto.setName(savedPatient.getName());
	        responseDto.setAge(savedPatient.getAge());
	        responseDto.setGender(savedPatient.getGender());
	        responseDto.setPhone(savedPatient.getPhone());
	        responseDto.setAddress(savedPatient.getAddress());
	        responseDto.setDob(savedPatient.getDob());
	        
	        return ResponseEntity.ok(responseDto);
	        
	    } catch (Exception e) {
	        System.err.println("Error updating profile: " + e.getMessage());
	        e.printStackTrace();
	        return ResponseEntity.badRequest()
	                .body("Error updating profile: " + e.getMessage());
	    }
	}
}