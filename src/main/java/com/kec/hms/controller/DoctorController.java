package com.kec.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kec.hms.model.Doctor;
import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.DoctorRepository;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
	
		@Autowired
		private DoctorService docdervice;
		
		  @Autowired
		   private UserRepository userRepo;
		  
		  @Autowired
		  private DoctorRepository doctorRepo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping
		public Page<Doctor> getAllDoctors(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue = "2") int size){//
				
			return docdervice.getAllDoctors(page, size);
		}
		
		@PreAuthorize("hasRole('DOCTOR')")
		@GetMapping("/me")
		public Doctor getMyRecord() {
		    String username = SecurityContextHolder.getContext().getAuthentication().getName();
		    User user = userRepo.findByUsername(username).orElseThrow();
		    return doctorRepo.findByUser(user).orElseThrow();
		}
	
	
	
	 	@PreAuthorize("hasRole('ADMIN')")
	    @PostMapping
	    public String createDoctor(@RequestBody Doctor doctor) {
	        doctorRepo.save(doctor);
	        return "Doctor created successfully";
	    }
	 
	 
	 	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
	    @PutMapping("/{id}")
	    public String updateDoctor(@PathVariable Integer id, @RequestBody Doctor updatedDoctor) {
	        Doctor doctor = doctorRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Doctor not found"));

	        // If current user is doctor, check ownership
	        String username = SecurityContextHolder.getContext().getAuthentication().getName();
	        User currentUser = userRepo.findByUsername(username).get();
	        if (currentUser.getRole().equals("DOCTOR") && !doctor.getUser().equals(currentUser)) {
	            throw new RuntimeException("Access denied");
	        }

	        doctor.setName(updatedDoctor.getName());
	        doctor.setAge(updatedDoctor.getAge());
	        doctor.setGender(updatedDoctor.getGender());
	        doctor.setSpecialty(updatedDoctor.getSpecialty());
	        doctorRepo.save(doctor);
	        return "Doctor updated successfully";
	    }
	
	 	@PreAuthorize("hasRole('ADMIN')")
	    @DeleteMapping("/{id}")
	    public String deleteDoctor(@PathVariable Integer id) {
	        doctorRepo.deleteById(id);
	        return "Doctor deleted successfully";
	    }
	
}
