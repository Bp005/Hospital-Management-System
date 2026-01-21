package com.kec.hms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kec.hms.model.Appointment;
import com.kec.hms.model.Doctor;
import com.kec.hms.model.Patient;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
List<Appointment> findByPatient(Patient patient);
    
    // Find all appointments for a specific doctor
    List<Appointment> findByDoctor(Doctor doctor);
    
    // Find appointments by date
    List<Appointment> findByAppointmentDate(LocalDate date);
    
    // Find appointments by status
    List<Appointment> findByStatus(String status);
    
    // Find appointments for a doctor on a specific date
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
    
    // Find appointments for a patient by status
    List<Appointment> findByPatientAndStatus(Patient patient, String status);
}
