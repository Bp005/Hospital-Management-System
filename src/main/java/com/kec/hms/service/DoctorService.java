package com.kec.hms.service;
import com.kec.hms.model.Doctor;
import com.kec.hms.model.User;
import com.kec.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    
    // NEW: Find doctor by user
    public Doctor findByUser(User user) {
        return doctorRepository.findByUser(user).orElse(null);
    }
    
    // NEW: Find doctors by specialization
    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }
}
//private DoctorRepository doctorRepo;
//
//
//
//
//private static final Logger log=LoggerFactory.getLogger(DoctorService.class);
//public Page<Doctor> getAllDoctors(int page, int size){
//	try {
//		System.out.println("Into service layer");
//		Pageable pageable=PageRequest.of(page, size);
//		return doctorRepo.findAll(pageable); 
//	} catch (Exception e) {
//		System.out.println("Error Message"+ e.getMessage());
//		log.error("An error  occured while fetching the doctors{}",e.getMessage());
//		return Page.empty();
//	}
//}
//
//public Doctor getDoctorById(Long id) {
//	try {
//	return doctorRepo.findById(id).orElse(null);
//	}catch (Exception e) {
//		log.error("Error occured while fetching the doctor by id:{}",e.getMessage());
//		return null;
//	}
//	
//}
//
//public void updateDoctor(int id,Doctor doctor) {//id identifies which data to update and doctor contains what data to update
//	try {
//		doctorRepo.save(doctor);
//	} catch (Exception e) {
//		log.error("Error occured while updating doctor",e.getMessage());
//	}
//} 
//public void addDoctor(Doctor doctor) {
//	try {
//		doctorRepo.save(doctor);
//	} catch (Exception e) {
//		log.error("Error occured while adding doctor:{}",e.getMessage());
//		
//	}
//}	
//	public void deleteDoctor(Long id) {
//		try {
//			doctorRepo.deleteById(id);
//		} catch (Exception e) {
//			log.error("Error occured while deleting doctor:{}",e.getMessage());
//			
//		}
//	}
//	