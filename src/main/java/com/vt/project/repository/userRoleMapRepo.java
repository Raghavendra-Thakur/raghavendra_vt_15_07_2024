package com.vt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vt.project.model.UserRoleMap;


public interface userRoleMapRepo extends JpaRepository<UserRoleMap, Integer> {
	
	UserRoleMap findByUid(int uid);;
	
}
