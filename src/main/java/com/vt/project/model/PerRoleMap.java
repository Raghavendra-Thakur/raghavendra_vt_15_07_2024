package com.vt.project.model;


import com.vt.project.utils.RolePermissionMapId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PER_ROLE_MAP")
public class PerRoleMap {
	
	@Id
	RolePermissionMapId id;

    @Column(name = "status", nullable = false)
    private Boolean status;
}

