package com.vt.project.model;


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
@Table(name = "USER_ROLES_MAP")
public class UserRoleMap {
	
	@Id
	@Column(name = "uid")
    private int uid;
	
	@Column(name = "rid")
    private int rid;
}
