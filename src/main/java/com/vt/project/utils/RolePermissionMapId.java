package com.vt.project.utils;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RolePermissionMapId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pid;
	private int rid;

	public RolePermissionMapId() {

	}

	public RolePermissionMapId(int pid, int rid) {
		super();
		this.pid = pid;
		this.rid = rid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

}