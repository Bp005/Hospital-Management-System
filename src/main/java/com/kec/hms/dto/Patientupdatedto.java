package com.kec.hms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Patientupdatedto {
	private String name;
    private Integer age;
    private String gender;
    private String phone;      // ← Make sure it's "phone" not "contact"
    private String address;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;     // ← Make sure DOB exists

}
