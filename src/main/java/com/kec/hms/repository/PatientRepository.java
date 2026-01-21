package com.kec.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kec.hms.model.Patient;
import com.kec.hms.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	Optional<Patient> findByUser(User user);
    Patient findByUserId(Long userId);

}
