package com.vt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vt.project.model.User;


public interface userRepo extends JpaRepository<User, Integer> {

	User findByEmail(String email);
}
