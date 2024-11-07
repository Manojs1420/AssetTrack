package com.titan.irgs.inventory.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "amcWarrantyDetails")
public class AmcWarranty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long warrantyId;

	private Long amcId;

	private Long assetId;

	private LocalDate warrantyFrom;

	private LocalDate warrantyTo;

	private boolean activeStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	private Date createdDate;
	
	private Date closedDate;
	
	private String ticketStatus;
	
	private Long vendorId;

	public Long getWarrantyId() {
		return warrantyId;
	} 

	public void setWarrantyId(Long warrantyId) {
		this.warrantyId = warrantyId;
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

	public Long getAmcId() {
		return amcId;
	}

	public void setAmcId(Long amcId) {
		this.amcId = amcId;
	}

	public LocalDate getWarrantyFrom() {
		return warrantyFrom;
	}

	public void setWarrantyFrom(LocalDate warrantyFrom) {
		this.warrantyFrom = warrantyFrom;
	}

	public LocalDate getWarrantyTo() {
		return warrantyTo;
	}

	public void setWarrantyTo(LocalDate warrantyTo) {
		this.warrantyTo = warrantyTo;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}


	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	
}
