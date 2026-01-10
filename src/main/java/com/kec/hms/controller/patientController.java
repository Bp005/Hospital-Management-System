package com.kec.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.kec.hms.service.PatientService;
@RestController

@RequestMapping("/api/v1/patients")
public class patientController {
	
	@Autowired
	private PatientService patientservice;
	
//	@Autowired
//	private PasswordEncoder passencode;
	
	
	@GetMapping
	public Page<Patient> getallPatients(@RequestParam (defaultValue= "0")int page,
										@RequestParam(defaultValue = "2") int size){
		System.out.println("fetching the patient");
		return patientservice.getAllPatients(page,size);
	}
	
	@PostMapping
	public String createPatient(@RequestBody Patient patient) {
		System.out.println("creating patient");
		//patient.setPassword(passencode.encode(patient.getPassword()));
		patientservice.addPatient(patient);
		return "Patient added";
	}

	@GetMapping("/{id}")
	public Patient getPatientById(@PathVariable int id) {
		System.out.println("fetching by id");
		return patientservice.getPatientById(id);
	}
	
	@PutMapping("/{id}")
	public String updatePatient(@PathVariable int id, @RequestBody Patient patient) {
		
		patientservice.updatePatient(id, patient);
		return "update success";
	}
	
	@DeleteMapping("/{id}")
	public String DeletePatient(@PathVariable int id) {
		
		patientservice.deletePatient(id);
		return "successfully deleted patient";
	}
	
}
