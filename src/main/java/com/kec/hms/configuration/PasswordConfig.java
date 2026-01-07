package com.kec.hms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //this class defines beans
public class PasswordConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
}
//PasswordEncoder is an interface we imported and bcryptpasswordencoder
//is one implementation
//in new bcryptencoder,u create new bcrypt encoder, spring stores it in memory and manages it.
//write it once spring uses it everywhere