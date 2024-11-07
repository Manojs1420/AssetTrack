package com.titan.irgs.master.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

@Entity
@Table(name = "vendor_equipment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vendorEquipmentId", scope = Long.class)
public class VendorEquipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorEquipmentId;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "vendorId", nullable = true)
	private Vendor vendor;
	
	private Long equipmentId;
	
	
	private long createdBy;
	
	private long updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public Long getVendorEquipmentId() {
		return vendorEquipmentId;
	}

	public void setVendorEquipmentId(Long vendorEquipmentId) {
		this.vendorEquipmentId = vendorEquipmentId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	
	
	
	
	

}
