package com.kec.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.kec.hms.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorService docdervice;
	
	@GetMapping
	public Page<Doctor> getAllDoctors(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue = "2") int size){//
			
		return docdervice.getAllDoctors(page, size);
	}
	
	@PostMapping
	public String addDoctors(@RequestBody Doctor doctor) {
		docdervice.addDoctor(doctor);
		return "Doctor Added";
	}
	
	@PutMapping("/{id}")
	public String updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
		docdervice.updateDoctor(id, doctor);
		//TODO: process PUT request
		
		return "Updated";
	}
	
	@DeleteMapping("/{id}")
	public String deleteDoctor(@PathVariable int id) {
		docdervice.deleteDoctor(id);
		return "deleted";
	}
	
	
}
