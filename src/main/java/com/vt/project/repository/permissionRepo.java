package com.vt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vt.project.model.Permission;
import java.util.List;


public interface permissionRepo extends JpaRepository<Permission, Integer> {
	
	Permission findById(int id);
}
