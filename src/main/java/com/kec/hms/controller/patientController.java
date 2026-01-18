package com.kec.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.PatientRepository;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.service.PatientService;

import io.micrometer.core.ipc.http.HttpSender.Request;
@RestController

@RequestMapping("/api/v1/patients")
public class patientController {
	
	@Autowired
	private PatientService patientservice;
	
	@Autowired
	private PatientRepository patientrepo;
	
	
	@Autowired
	private UserRepository userRepo;
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public List<Patient> getAll() {
        return patientrepo.findAll();
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@PostMapping
	public String createPatient(@RequestBody Patient patient) {
		System.out.println("creating patient");
		patientservice.addPatient(patient);
		return "Patient added";
	}
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@GetMapping("/{id}")
	public Patient getPatientById(@PathVariable int id) {
		System.out.println("fetching by id");
		return patientservice.getPatientById(id);
	}
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping("/me")
	public Patient getMyRecord() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepo.findByUsername(username).orElseThrow();
	    return patientrepo.findByUser(user).orElseThrow();
	}

	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@PutMapping("/{id}")
	public String updatePatient(@PathVariable int id, @RequestBody Patient patient) {
		
		patientservice.updatePatient(id, patient);
		return "update success";
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	@DeleteMapping("/{id}")
	public String DeletePatient(@PathVariable int id) {
		
		patientservice.deletePatient(id);
		return "successfully deleted patient";
	}
	
}
