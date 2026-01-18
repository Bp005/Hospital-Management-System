package com.kec.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kec.hms.model.Appointment;
import com.kec.hms.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    // CREATE
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    // READ ALL
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    // READ BY ID
    public Appointment getAppointmentById(int id) {
        return appointmentRepo.findById(id).orElse(null);
    }

    // UPDATE
    public Appointment updateAppointment(int id, Appointment appointment) {
        Appointment existing=getAppointmentById(id);
        existing.setDate(appointment.getDate());
        existing.setPatient(appointment.getPatient());
        existing.setDoctor(appointment.getDoctor());
    	 
        return appointmentRepo.save(existing);
    }

    // DELETE
    public void deleteAppointment(int id) {
        appointmentRepo.deleteById(id);
    }
}
