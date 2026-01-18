package com.kec.hms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kec.hms.model.Doctor;
import com.kec.hms.model.Patient;
import com.kec.hms.repository.DoctorRepository;



@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepo;
	
	
	
	
	private static final Logger log=LoggerFactory.getLogger(DoctorService.class);
	public Page<Doctor> getAllDoctors(int page, int size){
		try {
			System.out.println("Into service layer");
			Pageable pageable=PageRequest.of(page, size);
			return doctorRepo.findAll(pageable); 
		} catch (Exception e) {
			System.out.println("Error Message"+ e.getMessage());
			log.error("An error  occured while fetching the doctors{}",e.getMessage());
			return Page.empty();
		}
	}
	
	public Doctor getDoctorById(int id) {
		try {
		return doctorRepo.findById(id).orElse(null);
		}catch (Exception e) {
			log.error("Error occured while fetching the doctor by id:{}",e.getMessage());
			return null;
		}
		
	}
	
	public void updateDoctor(int id,Doctor doctor) {//id identifies which data to update and doctor contains what data to update
		try {
			doctorRepo.save(doctor);
		} catch (Exception e) {
			log.error("Error occured while updating doctor",e.getMessage());
		}
	} 
	public void addDoctor(Doctor doctor) {
		try {
			doctorRepo.save(doctor);
		} catch (Exception e) {
			log.error("Error occured while adding doctor:{}",e.getMessage());
			
		}
	}	
		public void deleteDoctor(int id) {
			try {
				doctorRepo.deleteById(id);
			} catch (Exception e) {
				log.error("Error occured while deleting doctor:{}",e.getMessage());
				
			}
		}
		
	
	
}
