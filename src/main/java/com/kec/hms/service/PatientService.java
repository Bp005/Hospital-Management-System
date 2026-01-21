package com.kec.hms.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.PatientRepository;


@Service
public class PatientService {
	
	private static final Logger log=LoggerFactory.getLogger(PatientService.class);
	
	@Autowired
	private PatientRepository patientrepo;
	
	
	
	public Page<Patient> getAllPatients(int page, int size){
		try {
			System.out.println("into service layer");
			//interact with the repo layer
			Pageable pageable = PageRequest.of(page, size);
			return patientrepo.findAll(pageable);
		}catch(Exception e) {
			System.out.println("Error Message"+e.getMessage());
			log.error("An error occured while fetching the patients: {}",e.getMessage());
			return null;
		}
		
	}
	
	
	public Patient getPatientById(Long id) {
		try {
			return patientrepo.findById(id).orElse(null);
		}catch(Exception e) {
			log.error("An error occured while fetching the patient by ID: {}",id,e.getMessage());

			System.out.println("ErrorMessage:"+ e.getMessage());
			return null;
		}
	}
	
	public void  updatePatient(Long id,Patient patient) {
		patient.setId(id); 
		patientrepo.save(patient);
	}
	
	public void addPatient(Patient patient) {
		patientrepo.save(patient);
	}
	
	public void deletePatient(Long id) {
		patientrepo.deleteById(id);
	}
	
	public Patient findByUser(User user) {
        return patientrepo.findByUser(user).orElse(null);
    }
}	
