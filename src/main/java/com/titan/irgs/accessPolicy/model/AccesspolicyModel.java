package com.titan.irgs.accessPolicy.model;

import java.util.Date;
import java.util.List;

public class AccesspolicyModel {
	
	private Long accesspolicyId;
	private String accesspolicyName;
	private Long moduleId;
	private String moduleName;
	
	private String verticalName;

	private String roleName;


	private Long subModuleId;
	private String subModuleName;

	private Date updatedOn;
	private Long webRoleId;
	private String webRoleName;

	private Date createdOn;
	private long createdBy;
	private long updatedBy;
	
	private List<Long> subModuleIds;
	


	public Long getAccesspolicyId() {
		return accesspolicyId;
	}

	public void setAccesspolicyId(Long accesspolicyId) {
		this.accesspolicyId = accesspolicyId;
	}

	public String getAccesspolicyName() {
		return accesspolicyName;
	}

	public void setAccesspolicyName(String accesspolicyName) {
		this.accesspolicyName = accesspolicyName;
	}

	

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(Long subModuleId) {
		this.subModuleId = subModuleId;
	}

	

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getWebRoleName() {
		return webRoleName;
	}

	public void setWebRoleName(String webRoleName) {
		this.webRoleName = webRoleName;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Long> getSubModuleIds() {
		return subModuleIds;
	}

	public void setSubModuleIds(List<Long> subModuleIds) {
		this.subModuleIds = subModuleIds;
	}

	

	


	
	
	
	
	

}
