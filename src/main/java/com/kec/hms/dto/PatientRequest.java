package com.kec.hms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PatientRequest {
	private String name;
    private int age;
    private String gender;
    public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	private String address;
    private String phone;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}


