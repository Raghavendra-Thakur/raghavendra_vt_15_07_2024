package com.vt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vt.project.model.UserRole;
import java.util.List;


public interface userRoleRepo extends JpaRepository<UserRole, Integer>{
	
	UserRole findById(int id);
}
