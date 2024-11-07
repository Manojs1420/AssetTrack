/*package com.titan.irgs.inventory.domain;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.inventory.enums.EquipmentInventoryStatus;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.Equipment;

*//**
 * This is InventoryDetail domain class which map the inventory_detail table
 *
 * 
 *//*
@Entity
@Table(name = "inventory_detail")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "inventoryDetailId", scope = Long.class)
public class InventoryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryDetailId;

	@Column(unique = true, nullable = false)
	private String serialNum;

	private Date warrantyFrom;

	private Date warrantyTo;

	private Date mfgDate;

	private Date installationDate;

	private String Description;

	@Enumerated(EnumType.STRING)
	private EquipmentInventoryStatus equipmentInventoryStatus;

	
	 * @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	 * 
	 * @JoinColumn(name = "equipmentId", nullable = false) private Equipment
	 * equipment;
	 

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "inventoryId", nullable = false)
	private Inventory inventory;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "assetId", nullable = false)
	private Asset asset;

	
	 * @OneToMany(fetch = FetchType.LAZY,mappedBy = "equipmentInventoryDetail")
	 * private List<ServiceRequest> serviceRequests = new
	 * ArrayList<ServiceRequest>(0);
	 

	private Long vendorId;

	private String vendorName;

	private long createdBy;

	private long updatedBy;

	private Long Qty;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Long getInventoryDetailId() {
		return inventoryDetailId;
	}

	public void setInventoryDetailId(Long inventoryDetailId) {
		this.inventoryDetailId = inventoryDetailId;
	}

	public Date getWarrantyFrom() {
		return warrantyFrom;
	}

	public void setWarrantyFrom(Date warrantyFrom) {
		this.warrantyFrom = warrantyFrom;
	}

	public Date getWarrantyTo() {
		return warrantyTo;
	}

	public void setWarrantyTo(Date warrantyTo) {
		this.warrantyTo = warrantyTo;
	}

	public Date getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public EquipmentInventoryStatus getEquipmentInventoryStatus() {
		return equipmentInventoryStatus;
	}

	public void setEquipmentInventoryStatus(EquipmentInventoryStatus equipmentInventoryStatus) {
		this.equipmentInventoryStatus = equipmentInventoryStatus;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
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

	public Long getQty() {
		return Qty;
	}

	public void setQty(Long qty) {
		Qty = qty;
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

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	

}
*/