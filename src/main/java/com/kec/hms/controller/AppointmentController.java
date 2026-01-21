package com.kec.hms.controller;

import com.kec.hms.dto.AppointmentRequest;
import com.kec.hms.dto.AppointmentResponse;
import com.kec.hms.model.Appointment;
import com.kec.hms.model.Doctor;
import com.kec.hms.model.Patient;
import com.kec.hms.model.User;
import com.kec.hms.repository.DoctorRepository;
import com.kec.hms.repository.PatientRepository;
import com.kec.hms.repository.UserRepository;
import com.kec.hms.service.AppointmentService;
import com.kec.hms.service.DoctorService;
import com.kec.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    // ============================================
    // ADMIN ENDPOINTS
    // ============================================
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentResponse> responses = convertToResponseList(appointments);
        return ResponseEntity.ok(responses);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        AppointmentResponse response = convertToResponse(appointment);
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        try {
            // FIXED: Convert to proper types
            Patient patient = patientService.getPatientById(request.getPatientId().longValue());
            Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
            
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setAppointmentDate(request.getAppointmentDate());
            appointment.setAppointmentTime(request.getAppointmentTime());
            appointment.setReason(request.getReason());
            appointment.setNotes(request.getNotes());
            appointment.setStatus("SCHEDULED");
            
            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            AppointmentResponse response = convertToResponse(savedAppointment);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error creating appointment: " + e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            
            if (request.getPatientId() != null) {
                // FIXED: Convert Long to int for getPatientById
                Patient patient = patientService.getPatientById(request.getPatientId().longValue());
                appointment.setPatient(patient);
            }
            
            if (request.getDoctorId() != null) {
                Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
                appointment.setDoctor(doctor);
            }
            
            appointment.setAppointmentDate(request.getAppointmentDate());
            appointment.setAppointmentTime(request.getAppointmentTime());
            appointment.setReason(request.getReason());
            appointment.setNotes(request.getNotes());
            
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            AppointmentResponse response = convertToResponse(updatedAppointment);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error updating appointment: " + e.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }
    
    // ============================================
    // PATIENT ENDPOINTS
    // ============================================
    
    @GetMapping("/my")
    public ResponseEntity<?> getMyAppointments(Authentication auth) {
        try {
            String username = auth.getName();
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Patient patient = patientRepo.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));
            
            List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient);
            List<AppointmentResponse> responses = convertToResponseList(appointments);
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error fetching appointments: " + e.getMessage());
        }
    }
    
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestBody AppointmentRequest request,
            Authentication auth) {
        try {
            String username = auth.getName();
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Patient patient = patientRepo.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found. Please complete your profile first."));
            
            Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
            
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setAppointmentDate(request.getAppointmentDate());
            appointment.setAppointmentTime(request.getAppointmentTime());
            appointment.setReason(request.getReason());
            appointment.setNotes(request.getNotes());
            appointment.setStatus("SCHEDULED");
            
            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            AppointmentResponse response = convertToResponse(savedAppointment);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error booking appointment: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long id,
            Authentication auth) {
        try {
            String username = auth.getName();
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Appointment appointment = appointmentService.getAppointmentById(id);
            
            // Check if this appointment belongs to the logged-in patient
            if (!appointment.getPatient().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(403)
                        .body("You can only cancel your own appointments");
            }
            
            Appointment cancelledAppointment = appointmentService.cancelAppointment(id);
            AppointmentResponse response = convertToResponse(cancelledAppointment);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error cancelling appointment: " + e.getMessage());
        }
    }
    
    // ============================================
    // DOCTOR ENDPOINTS
    // ============================================
    
    @GetMapping("/doctor/my")
    public ResponseEntity<?> getMyDoctorAppointments(Authentication auth) {
        try {
            String username = auth.getName();
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Doctor doctor = doctorRepo.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Doctor profile not found"));
            
            List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor);
            List<AppointmentResponse> responses = convertToResponseList(appointments);
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error fetching appointments: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {
        try {
            Appointment completedAppointment = appointmentService.completeAppointment(id);
            AppointmentResponse response = convertToResponse(completedAppointment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error completing appointment: " + e.getMessage());
        }
    }
    
    // ============================================
    // HELPER METHODS
    // ============================================
    
    private AppointmentResponse convertToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        
        // Patient info
        // FIXED: Convert Integer to Long
        response.setPatientId(appointment.getPatient().getId().longValue());
        response.setPatientName(appointment.getPatient().getName());
        
        // Doctor info
        response.setDoctorId(appointment.getDoctor().getId());
        response.setDoctorName(appointment.getDoctor().getName());
        response.setDoctorSpecialization(appointment.getDoctor().getSpecialization());
        
        // Appointment details
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setReason(appointment.getReason());
        response.setNotes(appointment.getNotes());
        response.setStatus(appointment.getStatus());
        
        return response;
    }
    
    private List<AppointmentResponse> convertToResponseList(List<Appointment> appointments) {
        List<AppointmentResponse> responses = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responses.add(convertToResponse(appointment));
        }
        return responses;
    }
}