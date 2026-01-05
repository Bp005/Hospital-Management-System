package com.kec.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kec.hms.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
	
}
