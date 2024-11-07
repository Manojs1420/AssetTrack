package com.titan.irgs.master.vo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.titan.irgs.inventory.enums.AmcStatus;

/**
 * This is Asset Value Object class which map the asset releated data which are
 * comming from client side/web page
 * 
 * @author 
 *
 */
public class AssetVO
{
	private Long assetId;
	
	private String assetCode;
	
	private String assetName;
	
	private Long modelId;
	
	private String modelName;
	
	private String modelNo;
	
	private long createdBy;

	private long updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private Long itemNo;
	
	private Long equipmentId;
	
	private String equipmentName;
	
	private String equipmentCode;
	
	private Long webMasterId;
	
	private String webMasterName;
	
	private Long brandId;
	
	private String brandName;
	
	private String farNo;
	
	private Long assetSpecificationId;
	
	private String assetSpecificationName;
	
	private String brandCode;

	private String remarks;
	
	private Long vendorId;

	private String vendorName;

	private LocalDate installationDate;

	private Long warrantyPeriod;
	private LocalDate warrantyEndDate;
	
	private String vendorCode;
	
	private AmcStatus amcStatus;
	
	private Long consumedQty;
	
	private String poNo;
	private String vendorInvoiceRef;
	
	private Long departmentId;
	
	private String departmentName;
	
	private String value;
	
	private String description;
	
	private Long PendingQty;
	
	private String isScrap;
	
	private String serialNumbers;
	
	private List<String> serialBasedAssets;
	private List<Long> serialIdsBasedAssets;
	
	private List<String> serialBasedAssetsForAsset;
	private List<Long> serialIdsBasedAssetsForAsset;
	
	public List<Long> getSerialIdsBasedAssets() {
		return serialIdsBasedAssets;
	}

	public void setSerialIdsBasedAssets(List<Long> serialIdsBasedAssets) {
		this.serialIdsBasedAssets = serialIdsBasedAssets;
	}

	public List<String> getSerialBasedAssets() {
		return serialBasedAssets;
	}

	public void setSerialBasedAssets(List<String> serialBasedAssets) {
		this.serialBasedAssets = serialBasedAssets;
	}

	public String getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(String serialNumbers) {
		this.serialNumbers = serialNumbers;
	}

	public String getIsScrap() {
		return isScrap;
	}

	public void setIsScrap(String isScrap) {
		this.isScrap = isScrap;
	}

	public Long getPendingQty() {
		return PendingQty;
	}

	public void setPendingQty(Long pendingQty) {
		PendingQty = pendingQty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getVendorInvoiceRef() {
		return vendorInvoiceRef;
	}

	public void setVendorInvoiceRef(String vendorInvoiceRef) {
		this.vendorInvoiceRef = vendorInvoiceRef;
	}

	public Long getConsumedQty() {
		return consumedQty;
	}

	public void setConsumedQty(Long consumedQty) {
		this.consumedQty = consumedQty;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public LocalDate getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(LocalDate installationDate) {
		this.installationDate = installationDate;
	}

	public Long getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Long warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public LocalDate getWarrantyEndDate() {
		return warrantyEndDate;
	}

	public void setWarrantyEndDate(LocalDate warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public AmcStatus getAmcStatus() {
		return amcStatus;
	}

	public void setAmcStatus(AmcStatus amcStatus) {
		this.amcStatus = amcStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
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

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
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

	public Long getItemNo() {
		return itemNo;
	}

	public void setItemNo(Long itemNo) {
		this.itemNo = itemNo;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
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

	public String getFarNo() {
		return farNo;
	}

	public void setFarNo(String farNo) {
		this.farNo = farNo;
	}

	public Long getAssetSpecificationId() {
		return assetSpecificationId;
	}

	public void setAssetSpecificationId(Long assetSpecificationId) {
		this.assetSpecificationId = assetSpecificationId;
	}

	public String getAssetSpecificationName() {
		return assetSpecificationName;
	}

	public void setAssetSpecificationName(String assetSpecificationName) {
		this.assetSpecificationName = assetSpecificationName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public List<String> getSerialBasedAssetsForAsset() {
		return serialBasedAssetsForAsset;
	}

	public void setSerialBasedAssetsForAsset(List<String> serialBasedAssetsForAsset) {
		this.serialBasedAssetsForAsset = serialBasedAssetsForAsset;
	}

	public List<Long> getSerialIdsBasedAssetsForAsset() {
		return serialIdsBasedAssetsForAsset;
	}

	public void setSerialIdsBasedAssetsForAsset(List<Long> serialIdsBasedAssetsForAsset) {
		this.serialIdsBasedAssetsForAsset = serialIdsBasedAssetsForAsset;
	}


}
