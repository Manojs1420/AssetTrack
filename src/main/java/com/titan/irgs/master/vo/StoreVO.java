package com.titan.irgs.master.vo;

import java.util.Date;
import java.util.List;

public class StoreVO {

	private Long storeId;

	private String storeCode;
	
	private String storeName;

	//private String fax1;

//	private String fax2;

//	private String storeLocality;

//	private Long cityId;

	//private Long stateId;

	//private Long countryId;

	//private Long regionId;

	private Long ownerTypeId;

	private Long storeServiceTypeId;

	private Long storeBusinessServiceTypeId;

	private Long webMasterId;
	
	private Long roleWiseDepartmentId;

//	private String cityName;

//	private String stateName;
	
//	private String stateGST;

//	private String countryName;

//	private String regionName;

	public Long getRoleWiseDepartmentId() {
		return roleWiseDepartmentId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setRoleWiseDepartmentId(Long roleWiseDepartmentId) {
		this.roleWiseDepartmentId = roleWiseDepartmentId;
	}

	private String ownerTypeName;

	private String storeServiceTypeName;

	private String storeBusinessServiceTypeName;

	private String webMasterName;

//	private String costcentre;

//	private String ownerName;

	private long createdBy;

	private long updatedBy;

	private Date createdOn;

	private Date updatedOn;

	private String reportingTo;

//	private boolean storeFlag;

//	private StoreStatus storeStatus;

//	private boolean starFlag;

	private List<StoreAllotedDetailsVO> storeAllotedDetailInfo;
	
	private Long reportingToId;

	private String reportingToUserName;
	
	private String reportingToRoleName;

	private Long reportingToRoleId;
	
	public Long getReportingToRoleId() {
		return reportingToRoleId;
	}

	public void setReportingToRoleId(Long reportingToRoleId) {
		this.reportingToRoleId = reportingToRoleId;
	}

	public Long getOwnerTypeId() {
		return ownerTypeId;
	}

	public void setOwnerTypeId(Long ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}

	public String getOwnerTypeName() {
		return ownerTypeName;
	}

	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Long getStoreServiceTypeId() {
		return storeServiceTypeId;
	}

	public void setStoreServiceTypeId(Long storeServiceTypeId) {
		this.storeServiceTypeId = storeServiceTypeId;
	}

	public Long getStoreBusinessServiceTypeId() {
		return storeBusinessServiceTypeId;
	}

	public void setStoreBusinessServiceTypeId(Long storeBusinessServiceTypeId) {
		this.storeBusinessServiceTypeId = storeBusinessServiceTypeId;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public String getStoreServiceTypeName() {
		return storeServiceTypeName;
	}

	public void setStoreServiceTypeName(String storeServiceTypeName) {
		this.storeServiceTypeName = storeServiceTypeName;
	}

	public String getStoreBusinessServiceTypeName() {
		return storeBusinessServiceTypeName;
	}

	public void setStoreBusinessServiceTypeName(String storeBusinessServiceTypeName) {
		this.storeBusinessServiceTypeName = storeBusinessServiceTypeName;
	}

	public String getWebMasterName() {
		return webMasterName;
	}

	public void setWebMasterName(String webMasterName) {
		this.webMasterName = webMasterName;
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

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public List<StoreAllotedDetailsVO> getStoreAllotedDetailInfo() {
		return storeAllotedDetailInfo;
	}

	public void setStoreAllotedDetailInfo(List<StoreAllotedDetailsVO> storeAllotedDetailInfo) {
		this.storeAllotedDetailInfo = storeAllotedDetailInfo;
	}

	public Long getReportingToId() {
		return reportingToId;
	}

	public void setReportingToId(Long reportingToId) {
		this.reportingToId = reportingToId;
	}

	public String getReportingToUserName() {
		return reportingToUserName;
	}

	public void setReportingToUserName(String reportingToUserName) {
		this.reportingToUserName = reportingToUserName;
	}

	public String getReportingToRoleName() {
		return reportingToRoleName;
	}

	public void setReportingToRoleName(String reportingToRoleName) {
		this.reportingToRoleName = reportingToRoleName;
	}
	
	
	

}
