package com.titan.irgs.role.model;

import java.util.Date;

public class RoleWiseDepartmentsVO {

	private Long roleWiseDepartmentsId;
	
	private Long roleId;
	private String roleName;
	private Date createdOn;
	private Date updatedOn;
	private Long departmentId;
	
	private Long webRoleId;
	
	private String departmentName;

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public Long getRoleWiseDepartmentsId() {
		return roleWiseDepartmentsId;
	}

	public void setRoleWiseDepartmentsId(Long roleWiseDepartmentsId) {
		this.roleWiseDepartmentsId = roleWiseDepartmentsId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
}
