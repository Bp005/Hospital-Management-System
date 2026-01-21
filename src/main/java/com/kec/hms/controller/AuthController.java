package com.kec.hms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.dto.AuthRequest;
import com.kec.hms.dto.DoctorRequest;
import com.kec.hms.dto.PatientRequest;
import com.kec.hms.model.Doctor;
import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.DoctorRepository;
import com.kec.hms.repository.PatientRepository;
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
	
	@Autowired
	private PatientRepository patientrepo;
	
	@Autowired
	private DoctorRepository doctorRepo;
	
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
	        
	        if ("PATIENT".equalsIgnoreCase(request.getRole())) {
	            PatientRequest patientreq=request.getPatientRequest();
	        	Patient patient = new Patient();
	        	 patient.setName(patientreq.getName());
	        	 patient.setAge(patientreq.getAge());
	        	 patient.setGender(patientreq.getGender());
	        	 patient.setAddress(patientreq.getAddress());
	        	 patient.setPhone(patientreq.getPhone());
	        	 patient.setDob(patientreq.getDob());
	        	 patient.setUser(user);
	        	 patientrepo.save(patient);

	        }
	        
	        if ("DOCTOR".equalsIgnoreCase(request.getRole())) {
	            DoctorRequest doctorReq = request.getDoctorRequest();

	        	Doctor doctor = new Doctor();
	        	doctor.setName(doctorReq.getName());
	        	doctor.setSpecialization(doctorReq.getSpecialty());
	        	doctor.setUser(user);
	        	doctorRepo.save(doctor);
	    
	        }

	        return request.getRole() + " registered successfully";
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

}
