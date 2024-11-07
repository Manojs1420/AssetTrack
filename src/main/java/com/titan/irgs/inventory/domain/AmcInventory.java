package com.titan.irgs.inventory.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.inventory.enums.MaintainanceType;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.webMaster.domain.WebMaster;

@Entity
@Table(name = "amcinventory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "amcId", scope = Long.class)
public class AmcInventory implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amcId;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "vendorId", nullable = true)
	private Vendor vendor;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "webMasterId", nullable = true)
	private WebMaster webMaster;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetWebMasterId", nullable = true)
	private WebMaster assetWebMaster;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetId", nullable = true)
	private Asset asset;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "userId",referencedColumnName = "id",nullable = true)
	private User user;
	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "amcInventory") private
	 * List<AmcInventoryDetail> amcInventoryDetail;
	 */	private Long maintainancePeriod;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public AmcInventory() {
		// TODO Auto-generated constructor stub
	}

	
	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
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

	public Long getAmcId() {
		return amcId;
	}

	public void setAmcId(Long amcId) {
		this.amcId = amcId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Long getMaintainancePeriod() {
		return maintainancePeriod;
	}

	public void setMaintainancePeriod(Long maintainancePeriod) {
		this.maintainancePeriod = maintainancePeriod;
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

	


	@Enumerated(EnumType.STRING)
	private AmcStatus amcStatus;

	@Enumerated(EnumType.STRING)
	private MaintainanceType maintainanceType;

	public MaintainanceType getMaintainanceType() {
		return maintainanceType;
	}

	public void setMaintainanceType(MaintainanceType maintainanceType) {
		this.maintainanceType = maintainanceType;
	}

	public AmcStatus getAmcStatus() {
		return amcStatus;
	}

	public void setAmcStatus(AmcStatus amcStatus) {
		this.amcStatus = amcStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WebMaster getAssetWebMaster() {
		return assetWebMaster;
	}

	public void setAssetWebMaster(WebMaster assetWebMaster) {
		this.assetWebMaster = assetWebMaster;
	}

	/*
	 * public List<AmcInventoryDetail> getAmcInventoryDetail() { return
	 * amcInventoryDetail; }
	 * 
	 * public void setAmcInventoryDetail(List<AmcInventoryDetail>
	 * amcInventoryDetail) { this.amcInventoryDetail = amcInventoryDetail; }
	 */

}
