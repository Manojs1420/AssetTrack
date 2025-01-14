package com.titan.irgs.inventory.vo;

import java.time.LocalDate;
import java.util.Date;

import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.enums.MaintainanceType;


public class AmcInventoryVO {

	private Long amcId;

	private Long inventoryId;
	
	private Long storeId;

	private String storeCode;
	
	private String storeName;
	
	private Long webMasterId;

	private String webMasterName;
	
	private String erNo;

	private Long assetId;

	private String assetName;
	
	private String farNo;
	
	private Long modelId;
	
	private String modelName;

	private Long vendorId;

	private String vendorCode;

	private String vendorName;

	private Long maintainancePeriod;

	private LocalDate maintainanceStartDate;

	private LocalDate maintainanceEndDate;

	private Long minMaintainanceGap;

	private LocalDate maintainanceValidity;

	private Long numberOfService;

	private String contractNumber;

	private String cancelAmc;

	private Boolean activeStatus;

	private LocalDate amcCancelledDate;

	private LocalDate amcCreatedDate;

	private String spares;

	private String cancellationRemarks;

	private String remarks;

	private String Description;

	private LocalDate installationDate;

	private Date createdOn;

	private Date updatedOn;
	private Long userId;
	private Long assetWebMasterId;
	//private List<AmcInventoryDetailVo> amcInventoryDetailVo;

	public Long getAmcId() {
		return amcId;
	}

	public void setAmcId(Long amcId) {
		this.amcId = amcId;
	}


	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public Long getMaintainancePeriod() {
		return maintainancePeriod;
	}

	public void setMaintainancePeriod(Long maintainancePeriod) {
		this.maintainancePeriod = maintainancePeriod;
	}

	public Long getMinMaintainanceGap() {
		return minMaintainanceGap;
	}

	public void setMinMaintainanceGap(Long minMaintainanceGap) {
		this.minMaintainanceGap = minMaintainanceGap;
	}

	
	public Long getNumberOfService() {
		return numberOfService;
	}

	public void setNumberOfService(Long numberOfService) {
		this.numberOfService = numberOfService;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCancelAmc() {
		return cancelAmc;
	}

	public void setCancelAmc(String cancelAmc) {
		this.cancelAmc = cancelAmc;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

		public String getSpares() {
		return spares;
	}

	public void setSpares(String spares) {
		this.spares = spares;
	}

	public String getCancellationRemarks() {
		return cancellationRemarks;
	}

	public void setCancellationRemarks(String cancellationRemarks) {
		this.cancellationRemarks = cancellationRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

		
	private AmcStatus amcStatus;

	public AmcStatus getAmcStatus() {
		return amcStatus;
	}

	public void setAmcStatus(AmcStatus amcStatus) {
		this.amcStatus = amcStatus;
	}
	private MaintainanceType maintainanceType;

	public MaintainanceType getMaintainanceType() {
		return maintainanceType;
	}

	public void setMaintainanceType(MaintainanceType maintainanceType) {
		this.maintainanceType = maintainanceType;
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

	public LocalDate getMaintainanceStartDate() {
		return maintainanceStartDate;
	}

	public void setMaintainanceStartDate(LocalDate maintainanceStartDate) {
		this.maintainanceStartDate = maintainanceStartDate;
	}

	public LocalDate getMaintainanceEndDate() {
		return maintainanceEndDate;
	}

	public void setMaintainanceEndDate(LocalDate maintainanceEndDate) {
		this.maintainanceEndDate = maintainanceEndDate;
	}

	public LocalDate getMaintainanceValidity() {
		return maintainanceValidity;
	}

	public void setMaintainanceValidity(LocalDate maintainanceValidity) {
		this.maintainanceValidity = maintainanceValidity;
	}

	public LocalDate getAmcCancelledDate() {
		return amcCancelledDate;
	}

	public void setAmcCancelledDate(LocalDate amcCancelledDate) {
		this.amcCancelledDate = amcCancelledDate;
	}

	public LocalDate getAmcCreatedDate() {
		return amcCreatedDate;
	}

	public void setAmcCreatedDate(LocalDate amcCreatedDate) {
		this.amcCreatedDate = amcCreatedDate;
	}

	public LocalDate getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(LocalDate installationDate) {
		this.installationDate = installationDate;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAssetWebMasterId() {
		return assetWebMasterId;
	}

	public void setAssetWebMasterId(Long assetWebMasterId) {
		this.assetWebMasterId = assetWebMasterId;
	}

	/*
	 * public List<AmcInventoryDetailVo> getAmcInventoryDetailVo() { return
	 * amcInventoryDetailVo; }
	 * 
	 * public void setAmcInventoryDetailVo(List<AmcInventoryDetailVo>
	 * amcInventoryDetailVo) { this.amcInventoryDetailVo = amcInventoryDetailVo; }
	 */
	

		
	

}
