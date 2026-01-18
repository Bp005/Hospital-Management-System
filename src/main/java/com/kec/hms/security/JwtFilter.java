package com.kec.hms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtil jwtUtil;

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
				// Skip auth endpoints
			    String path = request.getRequestURI();
			    if (path.startsWith("/auth/")) {
			        filterChain.doFilter(request, response);
			        return;
			    }
		
		        final String authHeader = request.getHeader("Authorization");

		        if (authHeader != null && authHeader.startsWith("Bearer ")) {
		            String token = authHeader.substring(7);

		            // validate token
		            if (jwtUtil.validateToken(token)) {
		                String username = jwtUtil.extractUsername(token);
		                String role = jwtUtil.extractRole(token);

		                // Set authentication in Spring Security
		                UsernamePasswordAuthenticationToken authToken =
		                        new UsernamePasswordAuthenticationToken(
		                                username,
		                                null,
		                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
		                        );

		                SecurityContextHolder.getContext().setAuthentication(authToken);
		            }
		        }
		            // Continue with request
		        filterChain.doFilter(request, response);
	}
}

	
	

