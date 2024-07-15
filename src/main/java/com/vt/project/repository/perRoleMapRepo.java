package com.vt.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vt.project.model.PerRoleMap;
import com.vt.project.utils.RolePermissionMapId;

public interface perRoleMapRepo extends JpaRepository<PerRoleMap, RolePermissionMapId>{
	
	@Query(value = "SELECT * FROM PER_ROLE_MAP WHERE rid = :rid", nativeQuery = true)
    List<PerRoleMap> findByRid(@Param("rid") int rid);
	
}
