package com.titan.irgs.inventory.vo;

import java.time.LocalDate;
import java.util.Date;

import com.titan.irgs.enumUtils.InventoryStatus;




public class InventoryVO {

	private Long inventoryId;

	private Long quantity;
	private Long userId;
	private String userName;
	private String regionName;

	private Long storeId;

	private String storeCode;
	
	private String storeName;

	private Long webMasterId;

	private String webMasterName;

	private long createdBy;

	private long updatedBy;

	private Date createdOn;

	private Date updatedOn;

	private Date manufactureDate;

	private String description;

	private String erNo;

	private Long assetId;

	private String assetName;
	
	private String farNo;
	
	private Long brandId;
	
	private String brandName;
	
	private Long modelId;
	
	private String modelName;
	
	private String assetCode;

	private String assetSpecification;
	
	private String brandCode;
	
	private String modelNo;
	
	private String QRCreated;
	
	private String remarks;
	
	private LocalDate allocationStartDate;
	
	private Long allottedPeriod;
	
	private LocalDate allocationEndDate;
	
	private InventoryStatus inventoryStatus;
	
	private Long overUsageDays;
	
	private String departmentName;
	
	private Long departmentId;
	
	private Long serialId;
	
	private String serialNumber;
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
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

	public LocalDate getAllocationStartDate() {
		return allocationStartDate;
	}

	public void setAllocationStartDate(LocalDate allocationStartDate) {
		this.allocationStartDate = allocationStartDate;
	}

	public Long getAllottedPeriod() {
		return allottedPeriod;
	}

	public void setAllottedPeriod(Long allottedPeriod) {
		this.allottedPeriod = allottedPeriod;
	}

	public LocalDate getAllocationEndDate() {
		return allocationEndDate;
	}

	public void setAllocationEndDate(LocalDate allocationEndDate) {
		this.allocationEndDate = allocationEndDate;
	}

	public InventoryStatus getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}


	public Long getOverUsageDays() {
		return overUsageDays;
	}

	public void setOverUsageDays(Long overUsageDays) {
		this.overUsageDays = overUsageDays;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getQRCreated() {
		return QRCreated;
	}

	public void setQRCreated(String qRCreated) {
		QRCreated = qRCreated;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
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

	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErNo() {
		return erNo;
	}

	public void setErNo(String erNo) {
		this.erNo = erNo;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getFarNo() {
		return farNo;
	}

	public void setFarNo(String farNo) {
		this.farNo = farNo;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetSpecification() {
		return assetSpecification;
	}

	public void setAssetSpecification(String assetSpecification) {
		this.assetSpecification = assetSpecification;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}



	
	

}
