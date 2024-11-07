package com.titan.irgs.accessPolicy.model;

import java.util.Date;


public class PermissionModel {
	
	
	private Long permissionId;


	
	private Long backEndApiId;
	
	
	private Long webRoleId;
	
	private String RoleName;
	
	private String verticalName;

	
	private Date createdOn;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	

	

	public Long getBackEndApiId() {
		return backEndApiId;
	}

	public void setBackEndApiId(Long backEndApiId) {
		this.backEndApiId = backEndApiId;
	}

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	

}
