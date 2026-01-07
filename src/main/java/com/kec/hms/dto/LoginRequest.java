package com.kec.hms.dto;

import lombok.Data;

@Data
public class LoginRequest {//only usend for request data, not an entity
	private String username;
	private String password;
}
