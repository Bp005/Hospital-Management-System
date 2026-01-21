package com.kec.hms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kec.hms.model.Doctor;
import com.kec.hms.model.User;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	 Optional<Doctor> findByUser(User user);
	    Doctor findByUserId(Long userId);
	    List<Doctor> findBySpecialization(String specialization);


}
