package com.kec.hms.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kec.hms.model.User;
import com.kec.hms.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if test user already exists
            if (userRepository.findByUsername("bijay") == null) {
                User user = new User();
                user.setUsername("bijay");
                // encode password properly
                user.setPassword(passwordEncoder.encode("mypassword"));
                user.setRole("ROLE_USER");
                userRepository.save(user);
                System.out.println("Test user created: bijay / mypassword");
            }
        };
    }
}
