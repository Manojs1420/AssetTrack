package com.titan.irgs.role.model;

import java.util.Date;
import java.util.List;



public class RoleModel {
	
	private Long roleId;
	private String roleName;
	private Date createdOn;
	private Date updatedOn;
	private long createdBy;
	private OperationTypeEnum operationTypeEnum;
	private long updatedBy;
	private boolean enabledStatus; 
	private Long webRoleId;
	private Long webMasterId;
	private Long reportingId;

	private List<Long> regionIds;
	
	private List<RoleWiseDepartmentsVO> roleWiseDepartmentsVOs;

	public List<RoleWiseDepartmentsVO> getRoleWiseDepartmentsVOs() {
		return roleWiseDepartmentsVOs;
	}

	public void setRoleWiseDepartmentsVOs(List<RoleWiseDepartmentsVO> roleWiseDepartmentsVOs) {
		this.roleWiseDepartmentsVOs = roleWiseDepartmentsVOs;
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

	public boolean isEnabledStatus() {
		return enabledStatus;
	}

	public void setEnabledStatus(boolean enabledStatus) {
		this.enabledStatus = enabledStatus;
	}

	

	public OperationTypeEnum getOperationType() {
		return operationTypeEnum;
	}

	public void setOperationType(OperationTypeEnum operationTypeEnum) {
		this.operationTypeEnum = operationTypeEnum;
	}

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public List<Long> getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(List<Long> regionIds) {
		this.regionIds = regionIds;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public Long getReportingId() {
		return reportingId;
	}

	public void setReportingId(Long reportingId) {
		this.reportingId = reportingId;
	}

	
	


	
	
	

}
