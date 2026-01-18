package com.kec.hms.dto;

public class AuthRequest {
    private String username;
    private String password;
    private String role; // PATIENT, DOCTOR, ADMIN

    private PatientRequest patientRequest; // optional
    private DoctorRequest doctorRequest; // optional

    // getters & setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public PatientRequest getPatientRequest() { return patientRequest; }
    public void setPatientRequest(PatientRequest patientRequest) { this.patientRequest = patientRequest; }

    public DoctorRequest getDoctorRequest() { return doctorRequest; }
    public void setDoctorRequest(DoctorRequest doctorRequest) { this.doctorRequest = doctorRequest; }
}
