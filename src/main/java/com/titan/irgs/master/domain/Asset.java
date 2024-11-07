package com.titan.irgs.master.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.inventory.enums.AmcStatus;
import com.titan.irgs.webMaster.domain.WebMaster;



/**
 * This is Asset domain class which map the asset table
 * @author birendra
 *
 */
@Entity
@Table(name = "asset")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "assetId", scope = Long.class)
public class Asset implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetId;
	
	@Column(unique = true, nullable = false)
	private String assetCode;
	
	private String assetName;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "modelId", nullable = false)
	private Model model;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "equipmentId",nullable = true)
	private Equipment equipment;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "departmentId",nullable = true)
	private Department department;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "webMasterId",nullable = true)
	private WebMaster webMaster;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetSpecificationId",nullable = true)
	private AssetSpecification assetSpecification;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "vendorId", nullable = true)
	private Vendor vendor;
	
	private long createdBy;

	private long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	/*@OneToMany(fetch = FetchType.LAZY,mappedBy = "asset")
	List<EquipmentInventoryDetail> equipmentInventoryDetail= new ArrayList<EquipmentInventoryDetail>(0);*/
	
	@Column(unique = true, nullable = false)	
	private String farNo;
	
	private Long originalQty;
	private Long consumedQty;
	
	private LocalDate installationDate;
	private Long warrantyPeriod;
	private LocalDate warrantyEndDate;
	
	@Enumerated(EnumType.STRING)
	private AmcStatus amcStatus;
	
	private String poNo;
	
	private String value;
	
	private String vendorInvoiceRef;
	
	private String poUpload;
	
	private String vendorInvoiceUpload;
	
	private String description;
	
	private String isScrap;
	
	@OneToMany(mappedBy = "asset",fetch = FetchType.LAZY)
	List<SerialNumber> serialNumbers=new ArrayList<SerialNumber>();
	
	
	public List<SerialNumber> getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(List<SerialNumber> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}

	public String getIsScrap() {
		return isScrap;
	}

	public void setIsScrap(String isScrap) {
		this.isScrap = isScrap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendorInvoiceUpload() {
		return vendorInvoiceUpload;
	}

	public void setVendorInvoiceUpload(String vendorInvoiceUpload) {
		this.vendorInvoiceUpload = vendorInvoiceUpload;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPoUpload() {
		return poUpload;
	}

	public void setPoUpload(String poUpload) {
		this.poUpload = poUpload;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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

	public AmcStatus getAmcStatus() {
		return amcStatus;
	}

	public void setAmcStatus(AmcStatus amcStatus) {
		this.amcStatus = amcStatus;
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

	public void setUpdatedOn(Date updatedOn) 
	{
		this.updatedOn = updatedOn;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Long getOriginalQty() {
		return originalQty;
	}

	public void setOriginalQty(Long originalQty) {
		this.originalQty = originalQty;
	}

	public Long getConsumedQty() {
		return consumedQty;
	}

	public void setConsumedQty(Long consumedQty) {
		this.consumedQty = consumedQty;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	public String getFarNo() {
		return farNo;
	}

	public void setFarNo(String farNo) {
		this.farNo = farNo;
	}

	public AssetSpecification getAssetSpecification() {
		return assetSpecification;
	}

	public void setAssetSpecification(AssetSpecification assetSpecification) {
		this.assetSpecification = assetSpecification;
	}

    
	
}
