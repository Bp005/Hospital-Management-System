package com.kec.hms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kec.hms.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
          .csrf(csrf -> csrf.disable()) // disable CSRF for POST/PUT/DELETE
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/auth/**").permitAll() // allow login/signup
              .requestMatchers("/doctor/**").hasRole("DOCTOR") // only doctor can access
              .requestMatchers("/patient/**").hasRole("USER")   // only patient can access
                

              .anyRequest().permitAll() // allow all others (for testing)
          )
          .addFilterBefore(
              jwtFilter,
              UsernamePasswordAuthenticationFilter.class
          );

        return http.build();
    }
}


//package com.kec.hms.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.kec.hms.security.JwtFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//          .csrf(csrf -> csrf.disable())
//          .authorizeHttpRequests(auth -> auth
//              .requestMatchers("/auth/**").permitAll()
//              .anyRequest().authenticated()
//          )
//          .addFilterBefore(
//              jwtFilter,
//              UsernamePasswordAuthenticationFilter.class
//          );
//
//        return http.build();
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//            .anyRequest().permitAll(); // allow all requests
//    }
//
//    
//}
