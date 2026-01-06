package com.kec.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kec.hms.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

}
