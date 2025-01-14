package com.titan.irgs.user.model;

import java.util.Date;
import java.util.List;

import com.titan.irgs.application.util.Status;

public class UserModel {
	
	private Long id;

    private Long createdBy;


    private Date createdOn;

    
    private Date modifiedOn;

    private String modifiedBy;
    
    private String firstName;
    
    private String lastName;
    
	private Status status;


	
	private String username;
	private String password;
	
	private String email;
	
	private String address;
	
	private String discription;
	
	private String phoneNo;


	private Long webRoleId;
	
	private String webRoleName;
	
	private Date lastLogin;
	
	private Long loginCount;
	
	
	private Long storeId;

	
	private Long verticleRoleId;
	
	private String verticleRoleName;

	private List<Long> regionIds;
	
	private String reportingToName;
	
	private String regionTypeName;
	
	private String operationTypeName;


	private String groupBusinessMasterName;
	
	private Long groupBusinessMasterId;
	

	
	private String isgroupBusiness;
	
	
	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private Boolean credentialsNonExpired;

	private Boolean isEnabled;
	
	private Long departmentId;
	
	private String departmentName;
	
	private String inventoryUser;
	
//	private StoreVO storeVO;
	
	private Long roleWiseDepartmentsId;
	
	private Long ownerTypeId;

	private Long storeServiceTypeId;
	
	private Long reportingToId;
	
	public Long getOwnerTypeId() {
		return ownerTypeId;
	}

	public void setOwnerTypeId(Long ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}

	public Long getStoreServiceTypeId() {
		return storeServiceTypeId;
	}

	public void setStoreServiceTypeId(Long storeServiceTypeId) {
		this.storeServiceTypeId = storeServiceTypeId;
	}

	public Long getReportingToId() {
		return reportingToId;
	}

	public void setReportingToId(Long reportingToId) {
		this.reportingToId = reportingToId;
	}

	public Long getRoleWiseDepartmentsId() {
		return roleWiseDepartmentsId;
	}

	public void setRoleWiseDepartmentsId(Long roleWiseDepartmentsId) {
		this.roleWiseDepartmentsId = roleWiseDepartmentsId;
	}



	public String getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(String inventoryUser) {
		this.inventoryUser = inventoryUser;
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

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getIsgroupBusiness() {
		return isgroupBusiness;
	}

	public void setIsgroupBusiness(String isgroupBusiness) {
		this.isgroupBusiness = isgroupBusiness;
	}

	public String getGroupBusinessMasterName() {
		return groupBusinessMasterName;
	}

	public void setGroupBusinessMasterName(String groupBusinessMasterName) {
		this.groupBusinessMasterName = groupBusinessMasterName;
	}

	public Long getGroupBusinessMasterId() {
		return groupBusinessMasterId;
	}

	public void setGroupBusinessMasterId(Long groupBusinessMasterId) {
		this.groupBusinessMasterId = groupBusinessMasterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	

	

	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public Long getVerticleRoleId() {
		return verticleRoleId;
	}

	public void setVerticleRoleId(Long verticleRoleId) {
		this.verticleRoleId = verticleRoleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Long getWebRoleId() {
		return webRoleId;
	}

	public void setWebRoleId(Long webRoleId) {
		this.webRoleId = webRoleId;
	}

	public String getWebRoleName() {
		return webRoleName;
	}

	public void setWebRoleName(String webRoleName) {
		this.webRoleName = webRoleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Long> getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(List<Long> regionIds) {
		this.regionIds = regionIds;
	}

	public String getVerticleRoleName() {
		return verticleRoleName;
	}

	public void setVerticleRoleName(String string) {
		this.verticleRoleName = string;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getReportingToName() {
		return reportingToName;
	}

	public void setReportingToName(String reportingToName) {
		this.reportingToName = reportingToName;
	}

	public String getRegionTypeName() {
		return regionTypeName;
	}

	public void setRegionTypeName(String regionTypeName) {
		this.regionTypeName = regionTypeName;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}
	
	
	
	
	
	
}
